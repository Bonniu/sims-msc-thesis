from datetime import datetime

from src.dto.LogDTO import LogDTO
from src.dto.LogStatus import LogStatus
from src.securityintelligence.anomaly.anomalyResultDTO import AnomalyResultDTO
from src.securityintelligence.siemalgorythm import SIEMAlgorythm


class Anomaly(SIEMAlgorythm):
    def __init__(self, logs: [LogDTO], period, kafka_template, db_conn):
        SIEMAlgorythm.__init__(self, logs, period)
        self.kafka_template = kafka_template
        self.db_conn = db_conn

    def run(self) -> AnomalyResultDTO:
        # filtering logs for every other user
        users = set([l.username for l in self.logs if l.username is not None])
        all_logs = self.logs.copy()
        # processing for all users
        for user in users:
            logs_user = [l for l in self.logs if l.username == user]
            all_logs = list(set(all_logs) - set(logs_user))
            # processing for one user
            result = self.process_one_user(logs_user, user)
            if result == False:
                self.kafka_template.send_message("block-user-topic", user, LogStatus.ERROR)
                return AnomalyResultDTO(user)
            pass

        pass

    def process_one_user(self, logs_user: [LogDTO], user):
        # controls for checking, if user logged/logged out correctly
        day_started = False
        curr_date_time = None
        start_date_time_tab = []
        end_date_time_tab = []
        day_finished = True

        # controls for learning habits

        for log in logs_user:
            # start day
            if log.message.__contains__("logged to system"):
                day_started = True
                curr_date_time = log.date_time
                start_date_time_tab.append(log.date_time)
                day_finished = False
                continue

            # end day
            if log.message.__contains__("logged out"):
                day_started = False
                day_finished = True
                end_date_time_tab.append(log.date_time)
                curr_date_time = None
                continue

            # other logs
            if day_started and curr_date_time != None and not day_finished:  # started day
                pass
            elif day_finished and not day_started and curr_date_time == None:  # finished day
                pass
            else:  # not logged user, but reserving/viewing visits
                print('Error')  # TODO do something with this user
                return {"username": user}

        # learning habits
        user_habits = Anomaly.get_user_habits(self.db_conn, user)
        start_date_avg = Anomaly.get_avg_time(start_date_time_tab)
        end_date_avg = Anomaly.get_avg_time(end_date_time_tab)
        nr_of_days_period = len(end_date_time_tab)
        if user_habits is None:
            Anomaly.insert_user_habits(self.db_conn, user, start_date_avg, end_date_avg, nr_of_days_period)
        else:
            start_hour_current = user_habits[1]
            start_minute_current = user_habits[2]
            end_hour_current = user_habits[3]
            end_minute_current = user_habits[4]
            nr_of_days_learned_current = user_habits[5]
            ready_to_monitor = user_habits[6]

            start_secs_current = start_hour_current * 3600 + start_minute_current * 60
            end_secs_current = end_hour_current * 3600 + end_minute_current * 60
            period = nr_of_days_learned_current + nr_of_days_period
            start_avg_in_secs = (start_secs_current * nr_of_days_learned_current + Anomaly.as_secs(
                start_date_avg) * nr_of_days_period) / (period)  # weight average start time
            end_avg_in_secs = (end_secs_current * nr_of_days_learned_current + Anomaly.as_secs(
                end_date_avg) * nr_of_days_period) / (period)  # weight average end time
            start_time_after_correct = Anomaly.time_from_secs(start_avg_in_secs)
            end_time_after_correct = Anomaly.time_from_secs(end_avg_in_secs)

            user_correct = Anomaly.check_if_user_is_in_time(user, start_date_time_tab, end_date_time_tab, start_time_after_correct, end_time_after_correct)
            if not user_correct:
                return False
            Anomaly.update_user_habits(self.db_conn, user, start_time_after_correct, end_time_after_correct, period)

        return True

    @staticmethod
    def check_if_user_is_in_time(user, new_start_date_time_tab, new_end_date_time_tab, start_time_after_correct, end_time_after_correct):
        for start_date in new_start_date_time_tab:
            start_date_new_secs = Anomaly.as_secs(start_date)
            start_time_correct = Anomaly.as_secs(start_time_after_correct)
            if start_time_correct + 900 < start_date_new_secs or start_time_correct - 900 > start_date_new_secs: # wyszło poza zakres, do zgłoszenia
                return False
        return True

    @staticmethod
    def get_avg_time(date_time_tab):
        time_tab_sec = [d.hour * 3600 + d.minute * 60 + d.second for d in date_time_tab]
        time_tab_sec_avg = int(sum(time_tab_sec) / len(time_tab_sec))
        return Anomaly.time_from_secs(time_tab_sec_avg)

    @staticmethod
    def as_secs(date_time):
        return date_time.hour * 3600 + date_time.minute * 60

    @staticmethod
    def time_from_secs(secs):
        hours = int(secs / 3600)
        minutes = int((secs % 3600) / 60)
        if secs % 60 > 30:
            minutes += 1
        if minutes == 60:
            hours += 1
            minutes = 0
        return datetime(2023, 1, 1, hours, minutes)

    @staticmethod
    def get_difference_from_times(datetime1, datetime2):
        datetime1_in_secs = datetime1.hour * 3600 + datetime1.minute * 60 + datetime1.second
        datetime2_in_secs = datetime2.hour * 3600 + datetime2.minute * 60 + datetime2.second
        secs = datetime2_in_secs - datetime1_in_secs
        return Anomaly.time_from_secs(secs)

    @staticmethod
    def get_user_habits(db_conn, user_id):
        db_cursor = db_conn.cursor()
        db_cursor.execute("SELECT * FROM t_users_habits where user_id='" + user_id + "' LIMIT 1")
        user_habits = db_cursor.fetchone()
        return user_habits

    @staticmethod
    def insert_user_habits(db_conn, user_id, start_date_avg, end_date_avg, nr_of_days_period):
        ready_to_monitor = False
        if nr_of_days_period >= 20:
            ready_to_monitor = True
        db_cursor = db_conn.cursor()
        query = "INSERT INTO t_users_habits VALUES ("
        query += "'" + user_id + "', "
        query += str(start_date_avg.hour) + ", "  # start_hour
        query += str(start_date_avg.minute) + ", "  # start_minute
        query += str(end_date_avg.hour) + ", "  # end_hour
        query += str(end_date_avg.minute) + ", "  # end_minute
        query += str(nr_of_days_period) + ", "  # nr_of_days_learned
        query += str(ready_to_monitor)  # ready_to_monitor
        query += ")"
        db_cursor.execute(query)
        db_conn.commit()

    @staticmethod
    def update_user_habits(db_conn, user_id, start_date_avg, end_date_avg, nr_of_days_period):
        ready_to_monitor = False
        if nr_of_days_period >= 20:
            ready_to_monitor = True
        db_cursor = db_conn.cursor()
        query = "UPDATE t_users_habits SET "
        query += "user_id = '" + user_id + "', "
        query += "start_hour = " + str(start_date_avg.hour) + ", "  # start_hour
        query += "start_minute = " + str(start_date_avg.minute) + ", "  # start_minute
        query += "end_hour = " + str(end_date_avg.hour) + ", "  # end_hour
        query += "end_minute = " + str(end_date_avg.minute) + ", "  # end_minute
        query += "nr_of_days_learned = " + str(nr_of_days_period) + ", "  # nr_of_days_learned
        query += "ready_to_monitor = " + str(ready_to_monitor)  # ready_to_monitor
        query += " where user_id = '" + user_id + "'"
        db_cursor.execute(query)
        db_conn.commit()

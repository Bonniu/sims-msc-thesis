from datetime import datetime

from src.dto.LogDTO import LogDTO
from src.securityintelligence.siemalgorythm import SIEMAlgorythm


class Anomaly(SIEMAlgorythm):
    def __init__(self, logs: [LogDTO], period, kafka_template):
        SIEMAlgorythm.__init__(self, logs, period)
        self.kafka_template = kafka_template

    def run(self):
        # filtering logs for every other user
        users = set([l.username for l in self.logs if l.username is not None])
        all_logs = self.logs.copy()
        # processing for all users
        for user in users:
            logs_user = [l for l in self.logs if l.username == user]
            all_logs = list(set(all_logs) - set(logs_user))
            # processing for one user
            self.process_one_user(logs_user, user)
            pass

        pass

    @staticmethod
    def process_one_user(logs_user: [LogDTO], user):
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
        start_date_avg = Anomaly.get_avg_time(start_date_time_tab)
        end_date_avg = Anomaly.get_avg_time(end_date_time_tab)
        day_length_avg = Anomaly.get_difference_from_times(start_date_avg, end_date_avg)

        # start_date_time_avg =
        pass

    @staticmethod
    def get_avg_time(date_time_tab):
        time_tab_sec = [d.hour * 3600 + d.minute * 60 + d.second for d in date_time_tab]
        time_tab_sec_avg = int(sum(time_tab_sec) / len(time_tab_sec))
        return Anomaly.time_from_secs(time_tab_sec_avg)

    @staticmethod
    def time_from_secs(secs):
        hours = int(secs / 3600)
        minutes = int((secs % 3600) / 60)
        if secs % 60 > 30:
            minutes += 1
        return datetime(2023, 1, 1, hours, minutes)

    @staticmethod
    def get_difference_from_times(datetime1, datetime2):
        datetime1_in_secs = datetime1.hour * 3600 + datetime1.minute * 60 + datetime1.second
        datetime2_in_secs = datetime2.hour * 3600 + datetime2.minute * 60 + datetime2.second
        secs = datetime2_in_secs - datetime1_in_secs
        return Anomaly.time_from_secs(secs)

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
        day_finished = True

        # controls for learning habits

        for log in logs_user:
            # start day
            if log.message.__contains__("logged to system"):
                day_started = True
                curr_date_time = log.date_time
                day_finished = False

            # end day
            if log.message.__contains__("logged out"):
                day_started = False
                day_finished = True
                curr_date_time = None

            # other logs
            if day_finished and not day_started and curr_date_time == None:  # not logged user, but reserving/viewing visits
                print('Error')  # TODO do something with this user
                return {"username": user}


        # learning habits

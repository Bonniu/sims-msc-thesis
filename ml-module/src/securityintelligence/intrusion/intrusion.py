from src.dto.LogStatus import LogStatus
from src.securityintelligence.intrusion.intrusionResultDTO import IntrusionResultDTO
from src.securityintelligence.siemalgorythm import SIEMAlgorythm


class Intrusion(SIEMAlgorythm):
    def __init__(self, logs, period, kafka_template):
        SIEMAlgorythm.__init__(self, logs, period)
        self.kafka_template = kafka_template

    def run(self):
        intrusion_results = []
        users = set([l.username for l in self.logs if l.username is not None])
        all_logs = self.logs.copy()
        # failed logins
        logging_in_out = [l for l in self.logs if l.message.__contains__("logged")]
        failed = 0
        failed_user = ""
        for log in logging_in_out:
            if log.message.__contains__("failed logged to system"):
                failed += 1
                failed_user = log.username
            if failed > 4:
                self.kafka_template.send_message("block-user-topic", log.username, LogStatus.ERROR)
                intrusion_results.append(IntrusionResultDTO(log.username, "user " + log.username + "failed to log in 5 times"))
        # BadRequestException
        for log in all_logs:
            if log.message.__contains__("BadRequestException") or log.message.__contains__("BadRequest"):
                intrusion_results.append(IntrusionResultDTO(log.username, log.message))
        return intrusion_results

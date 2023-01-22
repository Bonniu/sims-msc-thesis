from src.dto.LogDTO import LogDTO
from src.securityintelligence.siemalgorythm import SIEMAlgorythm


class Abnormality(SIEMAlgorythm):
    def __init___(self, logs: [LogDTO], period):
        SIEMAlgorythm.__init__(self, logs, period)

    def run(self):
        listInfo = [log for log in self.logs if log.logLevel == 'INFO']
        listError = [log for log in self.logs if log.logLevel == 'ERROR']
        listFatal = [log for log in self.logs if log.logLevel == 'FATAL']
        listWarn = [log for log in self.logs if log.logLevel == 'WARN']



        pass

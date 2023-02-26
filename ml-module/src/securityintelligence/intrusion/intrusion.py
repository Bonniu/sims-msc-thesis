from src.securityintelligence.intrusion.intrusionResultDTO import IntrusionResultDTO
from src.securityintelligence.siemalgorythm import SIEMAlgorythm


class Intrusion(SIEMAlgorythm):
    def __init__(self, logs, period):
        SIEMAlgorythm.__init__(self, logs, period)

    def run(self):
        users = set([l.username for l in self.logs if l.username is not None])
        all_logs = self.logs.copy()

        pass

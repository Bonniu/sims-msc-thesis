from src.securityintelligence.siemalgorythm import SIEMAlgorythm


class Intrusion(SIEMAlgorythm):
    def __init__(self, logs, period):
        SIEMAlgorythm.__init__(self, logs, period)

    def run(self):
        return True

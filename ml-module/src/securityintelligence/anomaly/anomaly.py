from src.securityintelligence.siemalgorythm import SIEMAlgorythm


class Anomaly(SIEMAlgorythm):
    def __init___(self, logs, period):
        SIEMAlgorythm.__init__(self, logs, period)

    def run(self):
        pass

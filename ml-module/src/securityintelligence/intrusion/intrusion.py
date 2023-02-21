from src.securityintelligence.intrusion.intrusionResultDTO import IntrusionResultDTO
from src.securityintelligence.siemalgorythm import SIEMAlgorythm


class Intrusion(SIEMAlgorythm):
    def __init__(self, logs, period):
        SIEMAlgorythm.__init__(self, logs, period)

    def run(self):
        logs = """
        Resolved [org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'POST' not supported]
        
        
        
        
        """
        return IntrusionResultDTO("kpiatek", logs)

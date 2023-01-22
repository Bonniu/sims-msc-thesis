import threading

from src.dto.LogStatus import LogStatus
from src.kafkatemplate.kafkaTemplate import KafkaTemplate
from src.securityintelligence.abnormality.abnormality import Abnormality
from src.securityintelligence.anomaly.anomaly import Anomaly
from src.securityintelligence.intrusion.intrusion import Intrusion


class Processor(threading.Thread):
    def __init__(self, logs: [], period: int, kafka_template: KafkaTemplate, backend_topic: str):
        threading.Thread.__init__(self)
        self.backend_topic = backend_topic
        self.logs = logs
        self.period = period
        self.kafka_template = kafka_template

    def run(self):
        try:
            Abnormality(self.logs, self.period).run()
            Anomaly(self.logs, self.period).run()
            Intrusion(self.logs, self.period).run()
            self.kafka_template.send_message(self.backend_topic, "Processing finished, no vulnerabilities found",
                                             LogStatus.CORRECT)
        except Exception as e:
            self.kafka_template.send_message(self.backend_topic, "Processing failed, reason: " + str(e),
                                             LogStatus.ERROR)

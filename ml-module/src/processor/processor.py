import threading
import traceback

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
            total_result = True
            abnormality_result = Abnormality(self.logs, self.period).run()
            Anomaly(self.logs, self.period).run()
            Intrusion(self.logs, self.period).run()

            if (abnormality_result.status == LogStatus.ERROR or abnormality_result.status == LogStatus.FATAL):
                total_result = False
                self.kafka_template.send_message(self.backend_topic,
                                                 "Processing finished, found errors: " + abnormality_result.get_error_logs_message(),
                                                 LogStatus.ERROR)
            if total_result:
                self.kafka_template.send_message(self.backend_topic, "Processing finished, no vulnerabilities found",
                                                 LogStatus.CORRECT)
        except Exception:
            exc = traceback.format_exc()
            self.kafka_template.send_message(self.backend_topic, "Processing failed, reason: " + str(exc),
                                             LogStatus.ERROR)

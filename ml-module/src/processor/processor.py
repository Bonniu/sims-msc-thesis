# import the threading module
import threading
from time import sleep

from src.dto.LogStatus import LogStatus
from src.kafkatemplate.kafkaTemplate import KafkaTemplate


class Processor(threading.Thread):
    def __init__(self, logs: [], kafka_template: KafkaTemplate, backend_topic: str):
        threading.Thread.__init__(self)
        self.backend_topic = backend_topic
        self.logs = logs
        self.kafka_template = kafka_template

    def run(self):
        try:
            sleep(3)


            self.kafka_template.send_message(self.backend_topic, "Processing finished, no vulnerabilities found", LogStatus.CORRECT)
        except:
            sleep(2)
            self.kafka_template.send_message(self.backend_topic, "Processing failed, most probably wrong input data", LogStatus.ERROR)

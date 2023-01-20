import json

from kafka import KafkaProducer

from src.dto.LogStatus import LogStatus


class KafkaTemplate:
    def __init__(self, producer: KafkaProducer):
        self.producer = producer

    def send_message(self, topic: str, message: str, status: LogStatus):
        dumps = json.dumps({"message": message, "status": status.name})
        encode = str.encode(dumps)
        self.producer.send(topic, encode)
        self.producer.flush()

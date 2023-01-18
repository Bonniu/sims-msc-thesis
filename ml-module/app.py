import configparser
import json
from threading import Event

from flask import Flask, request
from kafka import KafkaProducer

from LogDTO import LogDTO
from postgresConnector import PostgresConnector

config = configparser.ConfigParser()
config.read_file(open('properties.ini'))
app = Flask(__name__)
cur = PostgresConnector.get_db_connection(config['DATABASE']).cursor()

KAFKA_SERVER = config['KAFKA']['host']
TOPIC_NAME = "ml-result"

producer = KafkaProducer(
    bootstrap_servers=KAFKA_SERVER,
    api_version=(0, 11, 15)
)


@app.route('/ml', methods=['POST'])
def init_machine_learning():
    cur.execute('SELECT * FROM t_notification_channels;')
    mails = cur.fetchall()

    cur.execute('SELECT * FROM t_notification_mail_recipients;')
    recipients = cur.fetchall()

    logs_as_json = request.get_json()['logs']
    period = request.get_json()['period']

    logs = []
    for log_json in logs_as_json:
        log = LogDTO(log_json['message'], log_json['dateTime'], log_json['threadName'], log_json['logLevel'],
                     log_json['classPath'])
        logs.append(log)
        print(log)
    # print(logs)
    print(mails)
    print(recipients)
    dumps = json.dumps({"test": "Test"})
    encode = str.encode(dumps)
    producer.send(TOPIC_NAME, encode)
    producer.flush()
    return recipients


if __name__ == '__main__':
    app.run()
    # connection.close()

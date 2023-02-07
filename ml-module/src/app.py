import configparser
from datetime import datetime

from flask import Flask, request
from kafka import KafkaProducer

from src.database.postgresConnector import PostgresConnector
from src.dto.LogDTO import LogDTO
from src.dto.LogStatus import LogStatus
from src.kafkatemplate.kafkaTemplate import KafkaTemplate
from src.processor.processor import Processor

# configuration - config, app, db, kafka
config = configparser.ConfigParser()
config.read_file(open('properties.ini'))
app = Flask(__name__)
db_conn = PostgresConnector.get_db_connection(config['DATABASE'])
kafka_template = KafkaTemplate(KafkaProducer(
    bootstrap_servers=config['KAFKA']['host'],
    api_version=(0, 11, 15)
))


# main endpoint
@app.route('/ml', methods=['POST'])
def init_machine_learning():
    # Pobranie wysłanych z backendu logów
    logs_as_json = request.get_json()['logs']
    period = request.get_json()['period']

    # Przerobienie logów na DTO
    logs = []
    for log_json in logs_as_json:
        date_time_str = log_json['dateTime']
        if date_time_str[-9] == "T":
            date_time = datetime.strptime(date_time_str, '%Y-%m-%dT%H:%M:%S')
        elif date_time_str[-13] == "T":
            date_time = datetime.strptime(date_time_str, '%Y-%m-%dT%H:%M:%S.%f')
        else:
            date_time = datetime.strptime(date_time_str, '%Y-%m-%dT%H:%M')
        log = LogDTO(log_json['message'], date_time, log_json['threadName'], log_json['logLevel'],
                     log_json['classPath'], log_json['username'])
        logs.append(log)

    # Przetwarzanie w kierunku security
    processor = Processor(logs, period, kafka_template, config['KAFKA']['backend-reply-topic-name'], db_conn)
    processor.run()

    return LogStatus.PROCESSING.name


if __name__ == '__main__':
    app.run()


def database_connect():
    db_cursor = db_conn.cursor()
    db_cursor.execute('SELECT * FROM t_notification_channels;')
    mails = db_cursor.fetchall()
    print(mails)

    db_cursor.execute('SELECT * FROM t_notification_mail_recipients;')
    recipients = db_cursor.fetchall()
    print(recipients)

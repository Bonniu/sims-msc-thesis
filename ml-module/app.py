from flask import Flask, request

from LogDTO import LogDTO
from postgresConnector import PostgresConnector

app = Flask(__name__)
cur = PostgresConnector.get_db_connection().cursor()


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
    return recipients


if __name__ == '__main__':
    app.run()
    # connection.close()

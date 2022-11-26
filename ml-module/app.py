from flask import Flask, request

from postgresConnector import PostgresConnector

app = Flask(__name__)
cur = PostgresConnector.get_db_connection().cursor()

@app.route('/ml', methods=['POST'])
def init_machine_learning():
    cur.execute('SELECT * FROM t_notification_channels;')
    mails = cur.fetchall()

    cur.execute('SELECT * FROM t_notification_mail_recipients;')
    recipients = cur.fetchall()


    data = request.get_json()
    print(data)
    print(mails)
    print(recipients)
    return recipients


if __name__ == '__main__':
    app.run()
    # connection.close()


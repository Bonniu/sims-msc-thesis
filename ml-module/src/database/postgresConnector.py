import psycopg2 as psycopg2


class PostgresConnector:

    def __init__(self):
        pass

    @staticmethod
    def get_db_connection(config):
        conn = psycopg2.connect(host=config['host'],
                                port=config['port'],
                                database=config['name'],
                                user=config['username'],
                                password=config['password'])
        return conn

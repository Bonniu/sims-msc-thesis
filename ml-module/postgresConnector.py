import os

import psycopg2 as psycopg2


class PostgresConnector:

    def __init__(self):
        pass

    @staticmethod
    def get_db_connection():
        conn = psycopg2.connect(host='db-backend',
                                port=5432,
                                database='potentialsi',
                                user='postgres',
                                password='password')
        return conn
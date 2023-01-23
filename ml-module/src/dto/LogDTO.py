class LogDTO:
    def __init__(self, message, date_time, thread_name, log_level, classpath):
        self.message = message
        self.date_time = date_time
        self.thread_name = thread_name
        self.log_level = log_level
        self.classpath = classpath

    def __str__(self) -> str:
        return str(self.date_time) + " " + str(self.thread_name) \
               + " " + str(self.log_level) + " " + str(self.classpath) + ": " + str(self.message)

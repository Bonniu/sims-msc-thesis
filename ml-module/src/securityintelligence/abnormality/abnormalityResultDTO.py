from src.dto.LogStatus import LogStatus


class AbnormalityResultDTO:
    def __init__(self, logs_of_error_exceptions, logs_of_fatal_exceptions):
        self.logs_of_error_exceptions = logs_of_error_exceptions
        self.logs_of_fatal_exceptions = logs_of_fatal_exceptions
        self.status = LogStatus.CORRECT
        if len(logs_of_error_exceptions) != 0:
            self.status = LogStatus.ERROR
        if len(logs_of_fatal_exceptions) != 0:
            self.status = LogStatus.FATAL

    def get_error_logs_message(self):
        result = ""
        for log in self.logs_of_error_exceptions:
            result += str(log) + "\n"
        return result

    def get_fatal_logs_message(self):
        result = ""
        for log in self.logs_of_fatal_exceptions:
            result += str(log) + "\n"
        return result

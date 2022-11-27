class LogDTO:
    def __init__(self, message, dateTime, threadName, logLevel, classPath):
        self.message = message
        self.dateTime = dateTime
        self.threadName = threadName
        self.logLevel = logLevel
        self.classPath = classPath

    def __str__(self) -> str:
        return str(self.dateTime) + " " + str(self.threadName) \
               + " " + str(self.logLevel) + " " + str(self.classPath) + ": " + str(self.message)

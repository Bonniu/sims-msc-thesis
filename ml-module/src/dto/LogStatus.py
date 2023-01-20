from enum import Enum, auto


class LogStatus(Enum):
    FATAL = auto()
    ERROR = auto()
    WARN = auto()
    INFO = auto()
    CORRECT = auto()
    CONNECTION_ERROR = auto()
    TIMEOUT = auto()
    ALERT = auto()
    PROCESSING = auto()

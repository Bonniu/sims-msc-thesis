from src.dto.LogDTO import LogDTO
from src.securityintelligence.abnormality.abnormalityResultDTO import AbnormalityResultDTO
from src.securityintelligence.siemalgorythm import SIEMAlgorythm


class Abnormality(SIEMAlgorythm):
    def __init__(self, logs: [LogDTO], period):
        SIEMAlgorythm.__init__(self, logs, period)

    def run(self) -> AbnormalityResultDTO:
        log_list_error = [log for log in self.logs if log.log_level == 'ERROR']
        log_list_fatal = [log for log in self.logs if log.log_level == 'FATAL']

        # list of tuples with the words of the highest occurrence, sorted ascending
        sorted_dicts_error = self.extract_sorted_dicts(log_list_error)
        sorted_dicts_fatal = self.extract_sorted_dicts(log_list_fatal)

        # stop-list

        # more actions on error and fatal dictionaries - classification etc
        error_exceptions = self.filter_exceptions(sorted_dicts_error)
        fatal_exceptions = self.filter_exceptions(sorted_dicts_fatal)

        # finding full stack trace for given exception
        logs_of_error_exceptions = list(set(self.filter_logs_with_exceptions(error_exceptions, log_list_error)))
        logs_of_fatal_exceptions = list(set(self.filter_logs_with_exceptions(fatal_exceptions, log_list_fatal)))

        return AbnormalityResultDTO(logs_of_error_exceptions, logs_of_fatal_exceptions)

    @staticmethod
    def extract_sorted_dicts(listToParse):
        text = ""
        messages_list = [log.message for log in listToParse]
        for m in messages_list:
            text += m + " "
        text = Abnormality.clean_text(text)
        dicts = Abnormality.count_words(text)
        Abnormality.clean_map(dicts, len(messages_list))
        dicts = sorted(dicts.items(), key=lambda x: x[1])  # sorting dicts by values from highest to lowest
        dicts.reverse()
        return dicts

    @staticmethod
    def count_words(text: str):
        counts = dict()
        words = text.split(" ")

        for word in words:
            if word in counts:
                counts[word] += 1
            else:
                counts[word] = 1

        return counts

    @staticmethod
    def clean_text(text: str):
        # signs
        signs = [".", ",", ";", ":", "(", ")", "[", "]", "'", "-", "_", "+", "=", "%", "^", "&", "\"", "\\", "\t",
                 "\n", "/"]
        for s in signs:
            text = text.replace(s, " ")
        return text

    @staticmethod
    def clean_map(map: dict, nrOfLogs: int):
        for key in list(map.keys()):
            if Abnormality.is_int(key):
                map.pop(key)
                continue
            if len(key) < 4:
                map.pop(key)
                continue
            if map.get(key) <= nrOfLogs * 0.01:  # low value, almost no occurrence in text:
                map.pop(key)

    @staticmethod
    def is_int(s):
        try:
            int(s)
            return True
        except ValueError:
            return False

    @staticmethod
    def filter_exceptions(dicts: []):
        list = []
        for d in dicts:
            if d[0].__contains__("Exception") and d[0] != "Exception":
                list.append(d)
        return list

    @staticmethod
    def filter_logs_with_exceptions(exceptions, log_list):
        logs_of_exceptions = []
        for exc in exceptions:
            for log in log_list:
                if str(log).__contains__(exc[0]):
                    logs_of_exceptions.append(log)
        return logs_of_exceptions

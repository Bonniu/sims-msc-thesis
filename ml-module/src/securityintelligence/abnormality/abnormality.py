from src.dto.LogDTO import LogDTO
from src.securityintelligence.siemalgorythm import SIEMAlgorythm


class Abnormality(SIEMAlgorythm):
    def __init___(self, logs: [LogDTO], period):
        SIEMAlgorythm.__init__(self, logs, period)

    def run(self):
        list_error = [log for log in self.logs if log.log_level == 'ERROR']
        list_fatal = [log for log in self.logs if log.log_level == 'FATAL']

        # list of tuples with the words of the highest occurrence, sorted ascending
        sorted_dicts_error = self.extract_sorted_dicts(list_error)
        sorted_dicts_fatal = self.extract_sorted_dicts(list_fatal)

        # todo stop-list
        # todo more actions on error and fatal dictionaries - classification etc
        pass

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
        text = text.replace(".", " ")
        text = text.replace(",", " ")
        text = text.replace(";", " ")
        text = text.replace(":", " ")
        text = text.replace("(", " ")
        text = text.replace(")", " ")
        text = text.replace("]", " ")
        text = text.replace("[", " ")
        text = text.replace("'", " ")
        text = text.replace("-", " ")
        text = text.replace("=", " ")
        text = text.replace("+", " ")
        text = text.replace("_", " ")
        text = text.replace("&", " ")
        text = text.replace("\"", " ")
        text = text.replace("\\", " ")
        text = text.replace("/", " ")
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

package mgr.potentialsi.logs.processor;

import mgr.potentialsi.logs.LogLevel;
import mgr.potentialsi.logs.LogList;

public class LogPreprocessor {

    public static LogProcessorResult process(LogList logList) {
        var nrOfLogsPerSec = getFrequencyOfLogs(logList);
        var errors = logList.getLogs().stream()
                .filter(log -> log.getLogLevel().equals(LogLevel.ERROR) || log.getLogLevel().equals(LogLevel.FATAL)).toList();

        var logResultBuilder = LogProcessorResult.builder().nrOfLogsPerSec(nrOfLogsPerSec);
        if (errors.isEmpty()) {
            return logResultBuilder.status(LogStatus.CORRECT).build();
        }
        return logResultBuilder.status(LogStatus.ALERT).build();
    }

    public static int getFrequencyOfLogs(LogList logList) {
        return logList.getLogs().size() / logList.getPeriod();
    }
}

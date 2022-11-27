package mgr.potentialsi.logs.processor;

import mgr.potentialsi.logs.LogLevel;
import mgr.potentialsi.logs.LogList;

public class LogProcessor {

    public static LogProcessorResult process(LogList logList) {
        var nrOfLogsPerSec = getFrequencyOfLogs(logList);
        var status = LogProcessorStatus.CORRECT;

        var errors = logList.getLogs().stream()
                .filter(log -> log.getLogLevel().equals(LogLevel.ERROR) || log.getLogLevel().equals(LogLevel.FATAL)).toList();


        return LogProcessorResult.builder().nrOfLogsPerSec(nrOfLogsPerSec).status(status).build();
    }

    public static int getFrequencyOfLogs(LogList logList) {
        return logList.getLogs().size() / logList.getPeriod();
    }
}

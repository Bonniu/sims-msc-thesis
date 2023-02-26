package mgr.sims.logs.preprocessor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mgr.sims.logs.LogLevel;
import mgr.sims.logs.LogList;
import mgr.sims.logs.util.LogStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogPreprocessor {

    public static LogPreprocessorResult process(LogList logList) {
        var nrOfLogsPerSec = getFrequencyOfLogs(logList);
        var errors = logList.getLogs().stream()
                .filter(log -> log.getLogLevel().equals(LogLevel.ERROR) || log.getLogLevel().equals(LogLevel.FATAL)).toList();

        var logResultBuilder = LogPreprocessorResult.builder().nrOfLogsPerSec(nrOfLogsPerSec);
        if (errors.isEmpty()) {
            return logResultBuilder.status(LogStatus.CORRECT).build();
        }
        return logResultBuilder.status(LogStatus.ALERT).build();
    }

    public static int getFrequencyOfLogs(LogList logList) {
        return logList.getLogs().size() / logList.getPeriod();
    }
}

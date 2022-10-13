package mgr.potentialsi.logs.analyser;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mgr.potentialsi.logs.parser.LogParser;
import mgr.potentialsi.logs.reader.LogReader;
import mgr.potentialsi.logs.processor.LogProcessor;
import mgr.potentialsi.logs.processor.LogProcessorStatus;
import mgr.potentialsi.logs.util.LogLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@EnableScheduling
public class LogAnalyser {

    @Value("${log.reading.period}")
    private final int logParsingPeriod = 20000;

    @Scheduled(fixedDelay = logParsingPeriod)//cron = "1 * * * * *")
    public void parseLogs() {
        new ReaderThread(logParsingPeriod).start();
    }

    @AllArgsConstructor
    public static class ReaderThread extends Thread {

        private int logParsingPeriod;

        public void run() {
            LogLogger.logStart();
            try {
                var logsAsStrings = LogReader.readLogs();
                if (logsAsStrings.isEmpty()) {
                    LogLogger.logFinish();
                    return;
                }
                var logList = LogParser.parse(logsAsStrings, logParsingPeriod);
                var processorResult = LogProcessor.process(logList);

                LogProcessorStatus status = processorResult.getStatus();
                switch (status) {
                    case ALERT:
                        LogLogger.logFinishWithStatus(status, "warn");
                        return;
                    case FATAL:
                        LogLogger.logFinishWithStatus(status, "error");
                        return;
                    case CORRECT:
                    default:
                        LogLogger.logFinishWithStatus(status, "info");
                        return;
                }

            } catch (IOException e) {
                log.error("Analysing logs failed: ", e);
                throw new RuntimeException(e);
            }
        }

    }
}

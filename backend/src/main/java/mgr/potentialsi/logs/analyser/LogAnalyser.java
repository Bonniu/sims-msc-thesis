package mgr.potentialsi.logs.analyser;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mgr.potentialsi.logs.parser.LogParser;
import mgr.potentialsi.logs.processor.LogProcessorStatus;
import mgr.potentialsi.logs.reader.LogReader;
import mgr.potentialsi.logs.util.LogLogger;
import mgr.potentialsi.machinelearning.MLService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Slf4j
@Controller // -> @Component
@EnableScheduling
@RequiredArgsConstructor
public class LogAnalyser {

    @Value("${log.reading.period}")
    private final int logParsingPeriod = 1000;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MLService mLService;

//
//    @Scheduled(fixedDelay = logParsingPeriod)//cron = "1 * * * * *")
//    public void parseLogs() {
//        new ReaderThread(mLService, logParsingPeriod).start();
//    }

    @GetMapping("/kafka")
    public void kafkaTest() {
        String message = String.valueOf(Math.random());
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("topic-test", message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    @GetMapping("/mml")
    public void parseLogs() {
        new ReaderThread(mLService, logParsingPeriod).start();
    }

    public static class ReaderThread extends Thread {

        private final int logParsingPeriod;

        public ReaderThread(MLService mlService, int logParsingPeriod) {
            this.mlService = mlService;
            this.logParsingPeriod = logParsingPeriod;
        }

        private final MLService mlService;

        public void run() {
            LogLogger.logStart();
            try {
                var logsAsStrings = LogReader.readLogs();
                if (logsAsStrings.isEmpty()) {
                    LogLogger.logFinish();
                    return;
                }
                var logList = LogParser.parse(logsAsStrings, logParsingPeriod);

                var mlResult = mlService.sendToMLApi(logList);
//                var processorResult = LogProcessor.process(logList);

                LogProcessorStatus status = LogProcessorStatus.valueOf(mlResult); // FIXME temporary - will be returned
                // some more information about given data
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

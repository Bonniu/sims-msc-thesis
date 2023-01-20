package mgr.potentialsi.logs.analyser;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mgr.potentialsi.alerting.notification.NotificationService;
import mgr.potentialsi.alerting.notification.model.MessageType;
import mgr.potentialsi.logs.parser.LogParser;
import mgr.potentialsi.logs.preprocessor.LogPreprocessor;
import mgr.potentialsi.logs.reader.LogReader;
import mgr.potentialsi.logs.util.LogLogger;
import mgr.potentialsi.logs.util.LogStatus;
import mgr.potentialsi.machinelearning.MLService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

@Slf4j
@Controller // -> @Component
@EnableScheduling
@RequiredArgsConstructor
public class LogAnalyser {

    @Value("${log.reading.period}")
    private final int logParsingPeriod = 1000;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MLService mLService;
    private final NotificationService notificationService;

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
    public ResponseEntity<String> parseLogs() {
        new ReaderThread(mLService, logParsingPeriod, notificationService).start();
        return new ResponseEntity<String>("Processing started", HttpStatus.valueOf(200));
    }

    @AllArgsConstructor
    public static class ReaderThread extends Thread {

        private final MLService mlService;
        private final int logParsingPeriod;
        private final NotificationService notificationService;

        public void run() {
            log.info(MessageFormat.format("Starting analysing logs at [{0}]", new Date()));
            try {
                var logsAsStrings = LogReader.readLogs(); // wczytanie logów
                if (logsAsStrings.isEmpty()) {
                    log.info(MessageFormat.format("Finished analysing logs at [{0}]", new Date()));
                    return;
                }
                var logList = LogParser.parse(logsAsStrings, logParsingPeriod); // przetworzenie logów do analizy
                var mlStatus = LogStatus.valueOf(mlService.sendToMLApi(logList)); // wysłanie logów do ML, zwraca PROCESSING / ERROR

                // sprawdzenie, czy są błędy/ostrzeżenia, jeśli nie ma to nie zostanie wysłane dodatkowe powiadomienie
                var processorResult = LogPreprocessor.process(logList);
                var preprocessorStatus = processorResult.getStatus();

                // some more information about given data
                if (mlStatus == LogStatus.ERROR) {
                    String notificationMessage = "ML Module returned ERROR, check if everything works properly";
                    MessageType messageType = MessageType.ERROR;
                    notificationService.addNotification(notificationMessage, messageType);
                    return;
                }

                switch (preprocessorStatus) {
                    case ALERT:
                        String notificationMessage = "Preprocessor returned Error, may indicate some serious vulnerabilities";
                        MessageType messageType = MessageType.ERROR;
                        notificationService.addNotification(notificationMessage, messageType);
                        LogLogger.logFinishWithStatus(preprocessorStatus, "error");
                        return;
                    case CORRECT:
                    default:
//                        LogLogger.logFinishWithStatus(preprocessorStatus, "info");
                        return;
                }

            } catch (IOException e) {
                log.error("Analysing logs failed: ", e);
                String notificationMessage = "Analysing logs failed";
                MessageType messageType = MessageType.FATAL;
                notificationService.addNotification(notificationMessage, messageType);
                throw new RuntimeException(e);
            }
        }

    }
}

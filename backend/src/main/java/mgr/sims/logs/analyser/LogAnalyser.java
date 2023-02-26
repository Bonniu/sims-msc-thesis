package mgr.sims.logs.analyser;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mgr.sims.alerting.notification.NotificationService;
import mgr.sims.alerting.notification.model.MessageType;
import mgr.sims.exception.LogAnalysingException;
import mgr.sims.logs.LogService;
import mgr.sims.logs.parser.LogParser;
import mgr.sims.logs.preprocessor.LogPreprocessor;
import mgr.sims.logs.reader.LogReader;
import mgr.sims.logs.util.LogLogger;
import mgr.sims.logs.util.LogStatus;
import mgr.sims.machinelearning.MLService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

@Slf4j
@Controller
@EnableScheduling
@RequiredArgsConstructor
public class LogAnalyser {

    @Value("${log.reading.period}")
    private static final int LOG_PARSING_PERIOD = 1000;
    private final MLService mLService;
    private final NotificationService notificationService;
    private final LogService logService;

    @GetMapping("/mlInvalid")
    public ResponseEntity<String> parseLogs() {
        new ReaderThread(mLService, LOG_PARSING_PERIOD, notificationService, "logs", logService).start();
        return new ResponseEntity<>("Processing started", HttpStatus.valueOf(200));
    }

    @GetMapping("/mlTest")
    public ResponseEntity<String> parseLogsInitValid() {
        new ReaderThread(mLService, LOG_PARSING_PERIOD, notificationService, "logsV", logService).start();
        return new ResponseEntity<>("Processing started", HttpStatus.valueOf(200));
    }

    @AllArgsConstructor
    public static class ReaderThread extends Thread {

        private final MLService mlService;
        private final int logParsingPeriod;
        private final NotificationService notificationService;
        private final String logDirectoryPath;
        private final LogService logService;

        @SneakyThrows
        @Override
        public void run() {
            log.info(MessageFormat.format("Starting analysing logs at [{0}]", new Date()));
            try {
                var logsAsStrings = LogReader.readLogs(logDirectoryPath); // wczytanie logów
                if (logsAsStrings.isEmpty()) {
                    log.info(MessageFormat.format("Finished analysing logs at [{0}]", new Date()));
                    return;
                }
                var logList = LogParser.parse(logsAsStrings, logParsingPeriod); // przetworzenie logów do analizy
                logService.saveLogs(logList);  // zapisanie logów do bazy danych
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
                        MessageType messageType = MessageType.WARNING;
                        notificationService.addNotification(notificationMessage, messageType);
                        LogLogger.logFinishWithStatus(preprocessorStatus, "error");
                        return;
                    case CORRECT:
                    default:
                }

            } catch (IOException e) {
                log.error("Analysing logs failed: ", e);
                String notificationMessage = "Analysing logs failed";
                MessageType messageType = MessageType.FATAL;
                notificationService.addNotification(notificationMessage, messageType);
                throw new LogAnalysingException(e);
            }
        }

    }
}

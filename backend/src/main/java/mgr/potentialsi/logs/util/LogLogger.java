package mgr.potentialsi.logs.util;

import lombok.extern.slf4j.Slf4j;
import mgr.potentialsi.logs.processor.LogProcessorStatus;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Date;

@Slf4j
@Component
public class LogLogger {
    public static void logStart() {
        log("info", MessageFormat.format("Starting analysing logs at [{0}]", new Date()));
    }

    public static void logFinish() {
        log("info", MessageFormat.format("Finished analysing logs at [{0}]", new Date()));
    }

    public static void logFinishWithStatus(LogProcessorStatus status, String type) {
        log(type, MessageFormat.format("Finished analysing logs at [{0}] with {1} status", new Date(), status));
    }

    public static void log(String type, String message) {
        switch (type.toLowerCase()) {
            case "trace" -> log.trace(message);
            case "debug" -> log.debug(message);
            case "warn" -> log.warn(message);
            case "error" -> log.error(message);
            default -> log.info(message);
        }
    }


}

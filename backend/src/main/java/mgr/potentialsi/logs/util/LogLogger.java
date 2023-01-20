package mgr.potentialsi.logs.util;

import lombok.extern.slf4j.Slf4j;
import mgr.potentialsi.logs.processor.LogStatus;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Date;

@Slf4j
@Component
public class LogLogger {

    public static void logFinishWithStatus(LogStatus status, String type) {
        log(type, MessageFormat.format("{0} - Finished analysing logs at [{1}]", status, new Date()));
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

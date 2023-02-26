package mgr.sims.alerting.notification.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mgr.sims.alerting.notification.model.MessageType;
import mgr.sims.logs.util.LogStatus;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogStatusMessageTypeMapper {

    private static final Map<LogStatus, MessageType> logStatusMap = initLogStatusMap();

    public static LogStatus toLogStatus(MessageType messageType) {
        return logStatusMap
                .entrySet()
                .stream()
                .filter(entry -> messageType.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();
    }

    public static MessageType toMessageType(LogStatus logStatus) {
        return logStatusMap.get(logStatus);
    }

    public static Map<LogStatus, MessageType> initLogStatusMap() {
        var logStatusMap = new HashMap<LogStatus, MessageType>();
        logStatusMap.put(LogStatus.CORRECT, MessageType.INFO);
        logStatusMap.put(LogStatus.PROCESSING, MessageType.INFO);
        logStatusMap.put(LogStatus.ALERT, MessageType.WARNING);
        logStatusMap.put(LogStatus.ERROR, MessageType.ERROR);
        logStatusMap.put(LogStatus.CONNECTION_ERROR, MessageType.ERROR);
        logStatusMap.put(LogStatus.TIMEOUT, MessageType.ERROR);
        logStatusMap.put(LogStatus.FATAL, MessageType.FATAL);
        return logStatusMap;
    }
}

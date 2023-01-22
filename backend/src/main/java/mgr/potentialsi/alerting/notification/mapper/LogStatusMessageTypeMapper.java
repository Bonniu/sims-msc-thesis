package mgr.potentialsi.alerting.notification.mapper;

import mgr.potentialsi.alerting.notification.model.MessageType;
import mgr.potentialsi.logs.util.LogStatus;

import java.util.HashMap;
import java.util.Map;

public class LogStatusMessageTypeMapper {

    private static final HashMap<LogStatus, MessageType> logStatusMap = initLogStatusMap();

    public static LogStatus toLogStatus(MessageType messageType) {
        LogStatus logStatus = logStatusMap
                .entrySet()
                .stream()
                .filter(entry -> messageType.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();
        return logStatus;
    }

    public static MessageType toMessageType(LogStatus logStatus) {
        MessageType messageType = logStatusMap.get(logStatus);
        return messageType;
    }

    public static HashMap<LogStatus, MessageType> initLogStatusMap() {
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

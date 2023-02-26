package mgr.sims.cdc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mgr.sims.alerting.notification.NotificationService;
import mgr.sims.alerting.notification.mapper.LogStatusMessageTypeMapper;
import mgr.sims.alerting.notification.model.MessageType;
import mgr.sims.logs.util.LogProcessorResult;
import mgr.sims.logs.util.LogStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListenerKafka {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;
    private final List<LogStatus> wrongStatuses = Arrays.asList(LogStatus.ALERT, LogStatus.ERROR, LogStatus.FATAL, LogStatus.TIMEOUT, LogStatus.TIMEOUT);

    @KafkaListener(topics = "${ml-module.kafka-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) throws IOException {
        var mlResult = objectMapper.readValue(message, LogProcessorResult.class);
        if (wrongStatuses.contains(mlResult.getStatus())) {
            log.error("Received response from ml-module");
            MessageType messageType = LogStatusMessageTypeMapper.toMessageType(mlResult.getStatus());
            notificationService.addNotification(mlResult.getMessage(), messageType);
            return;
        }
        log.info("Received response from ml-module: " + mlResult);
    }
}

package mgr.potentialsi.cdc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableIntegration
@Slf4j
public class ListenerKafka {

    @KafkaListener(topics = "ml-result", groupId = "groupId")
    public void consume(String message) {
        log.info("Received message: " + message);
    }
}

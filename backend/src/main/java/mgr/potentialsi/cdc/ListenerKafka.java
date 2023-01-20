package mgr.potentialsi.cdc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mgr.potentialsi.logs.util.LogProcessorResult;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListenerKafka {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "ml-result", groupId = "groupId")
    public void consume(String message) throws IOException {
        var mlResult = objectMapper.readValue(message, LogProcessorResult.class);
        log.info("Received message: " + mlResult);

        // TODO - notifications after returned from python
    }
}

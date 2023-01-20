package mgr.potentialsi.machinelearning;

import lombok.RequiredArgsConstructor;
import mgr.potentialsi.logs.LogList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MLService {

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, LogList> kafkaTemplate;
    @Value("${ml-module.host}")
    private String mlModuleHost;

    public String sendToMLApi(LogList logList) {
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(mlModuleHost, logList, String.class);
        return stringResponseEntity.getBody();
    }
}

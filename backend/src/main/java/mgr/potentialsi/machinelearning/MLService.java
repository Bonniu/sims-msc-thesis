package mgr.potentialsi.machinelearning;

import lombok.RequiredArgsConstructor;
import mgr.potentialsi.logs.LogList;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MLService {

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, LogList> kafkaTemplate;

    public String sendToMLApi(LogList logList) {
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://localhost:5000/ml", logList, String.class);
        return "";
    }
}

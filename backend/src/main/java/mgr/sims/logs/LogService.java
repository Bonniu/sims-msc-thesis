package mgr.sims.logs;

import lombok.RequiredArgsConstructor;
import mgr.sims.logs.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public void saveLogs(LogList logList) {
        logRepository.saveAll(logList.getLogs());
    }

    public List<Log> getLogs() {
        return logRepository.findAll();
    }
}

package mgr.potentialsi.logs;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Log {

    private String message;
    private LocalDateTime dateTime;
    private String threadName;
    private LogLevel logLevel;
    private String classPath;

}

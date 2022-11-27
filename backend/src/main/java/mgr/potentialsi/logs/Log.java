package mgr.potentialsi.logs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Log {

    private String message;
    private String dateTime;
    private String threadName;
    private LogLevel logLevel;
    private String classPath;

}

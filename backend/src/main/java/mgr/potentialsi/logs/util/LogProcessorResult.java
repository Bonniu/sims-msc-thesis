package mgr.potentialsi.logs.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LogProcessorResult {

    private String message;
    private LogStatus status;

}

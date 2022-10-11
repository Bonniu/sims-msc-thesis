package mgr.potentialsi.logs.processor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogProcessorResult {

    private int nrOfLogsPerSec;
    private LogProcessorStatus status;


}

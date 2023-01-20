package mgr.potentialsi.logs.preprocessor;

import lombok.Builder;
import lombok.Data;
import mgr.potentialsi.logs.util.LogStatus;

@Data
@Builder
public class LogPreprocessorResult {

    private int nrOfLogsPerSec;
    private LogStatus status;

}

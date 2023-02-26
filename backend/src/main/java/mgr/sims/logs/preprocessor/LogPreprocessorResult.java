package mgr.sims.logs.preprocessor;

import lombok.Builder;
import lombok.Data;
import mgr.sims.logs.util.LogStatus;

@Data
@Builder
public class LogPreprocessorResult {

    private int nrOfLogsPerSec;
    private LogStatus status;

}

package mgr.potentialsi.logs.processor;

import mgr.potentialsi.logs.Log;
import mgr.potentialsi.logs.LogList;

import java.util.List;

public class LogProcessor {

    public static LogProcessorResult parse(LogList logList) {
        var nrOfLogsPerSec = getFrequencyOfLogs(logList);
        var status = LogProcessorStatus.CORRECT;




        return LogProcessorResult.builder().nrOfLogsPerSec(nrOfLogsPerSec).status(status).build();
    }

    public static int getFrequencyOfLogs(LogList logList) {
        return logList.getLogs().size() / logList.getPeriod();
    }
}

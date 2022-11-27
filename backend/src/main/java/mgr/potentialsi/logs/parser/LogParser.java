package mgr.potentialsi.logs.parser;


import lombok.extern.slf4j.Slf4j;
import mgr.potentialsi.logs.Log;
import mgr.potentialsi.logs.LogLevel;
import mgr.potentialsi.logs.LogList;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LogParser {

    /*
     * Log parser used to convert List<String> logs into List<Log> objects
     * logs with '###' are generated as mock users for breaking into system defense
     * */
    public static LogList parse(List<String> strLogs, int logParsingPeriod) {
        var logs = new ArrayList<Log>();
        for (var s : strLogs) {
            try {
                log.debug(MessageFormat.format("Splitting {0} by {1} ", s));

                if (s.contains("###")) { // mock logs
                    s = s.split("###")[1];
                }
                var stringDateTime = s.substring(0, 25).trim();
                var logLevel = s.substring(25, 30).trim();
                var pid = s.substring(30, 36).trim();
                var threadName = s.substring(41, 56).trim();
                var loggerName = s.substring(57, 99).trim();
                var message = s.substring(100).trim();

                var dateTime = LocalDateTime.parse(stringDateTime.replace(" ", "T"));
                Log build = Log.builder()
                        .dateTime(dateTime.toString())
                        .threadName(threadName)
                        .logLevel(LogLevel.valueOf(logLevel))
                        .classPath(loggerName)
                        .message(message)
                        .build();
                logs.add(build);
            } catch (Exception e) {
                log.error("Parsing logs failed: ", e);
            }
        }
        return new LogList(logs, logParsingPeriod);
    }
}

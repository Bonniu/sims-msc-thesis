package mgr.potentialsi.logs.parser;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mgr.potentialsi.logs.Log;
import mgr.potentialsi.logs.LogLevel;
import mgr.potentialsi.logs.LogList;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogParser {

    /*
     * Log parser used to convert List<String> logs into List<Log> objects
     * logs with '###' are generated as mock users for breaking into system defense
     * */
    public static LogList parse(List<String> strLogs, int logParsingPeriod) {
        var logs = new ArrayList<Log>();
        for (var s : strLogs) {
            try {
                var splitter = "###";
                log.debug(MessageFormat.format("Splitting {0} by {1} ", s, splitter));

                if (s.contains(splitter)) { // mock logs
                    s = s.split(splitter)[1];
                }
                var stringDateTime = s.substring(0, 25).trim();
                var logLevel = s.substring(25, 30).trim(); // pid - 30, 36
                var threadName = s.substring(41, 56).trim();
                var loggerName = s.substring(57, 99).trim();
                var message = s.substring(100).trim();
                String username = null;
                var messageUserSplitter = " - ";
                if (message.contains(messageUserSplitter)) {
                    var splitMessage = message.split(messageUserSplitter);
                    var usernameTmp = splitMessage[0];
                    if (!"HikariPool-1".equals(usernameTmp) && !usernameTmp.contains(" ")) {
                        message = splitMessage[1];
                        username = usernameTmp;
                    }
                }
                var dateTime = LocalDateTime.parse(stringDateTime.replace(" ", "T"));
                Log build = Log.builder()
                        .dateTime(dateTime.toString())
                        .threadName(threadName)
                        .logLevel(LogLevel.valueOf(logLevel))
                        .classPath(loggerName)
                        .message(message)
                        .username(username)
                        .build();
                logs.add(build);
            } catch (Exception e) {
                log.error("Parsing logs failed: ", e);
            }
        }
        return new LogList(logs, logParsingPeriod);
    }
}

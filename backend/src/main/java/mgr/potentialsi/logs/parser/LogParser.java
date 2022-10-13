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

    private static final String delimiter = "\\|"; // "|"

    public static LogList parse(List<String> strLogs, int logParsingPeriod) {
        var logs = new ArrayList<Log>();
        for (var s : strLogs) {
            try {
                log.debug(MessageFormat.format("Splitting {0} by {1} ", s, delimiter));
                String[] strings = s.split(delimiter); // sztywna struktura logów, może to będzie trzeba poprawić
                String[] classPathAndMessage = strings[3].split("~");
                var dateTime = LocalDateTime.parse(strings[0].trim().replace(" ", "T"));
                Log build = Log.builder()
                        .dateTime(dateTime)
                        .threadName(strings[1].trim())
                        .logLevel(LogLevel.valueOf(strings[2].trim()))
                        .classPath(classPathAndMessage[0].trim())
                        .message(classPathAndMessage[1].trim())
                        .build();
                logs.add(build);
            } catch (Exception e) {
                log.error("Parsing logs failed: ", e);
            }
        }
        return new LogList(logs, logParsingPeriod);
    }
}

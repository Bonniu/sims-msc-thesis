package mgr.sims.logs.reader;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogReader {

    /*
     * Read logs from directory 'logs'
     * Returns list of logs as String - one log line = one object
     * Log lines start with '$'
     * */
    public static List<String> readLogs(String logDirectoryPath) throws IOException {
        var delimiter = "$";
        List<File> logFiles = getFileList(logDirectoryPath);
        if (logFiles.isEmpty()) {
            log.info("Given log directory is invalid, or is not a directory, or log directory is empty");
            return new ArrayList<>();
        }
        var logs = new ArrayList<String>();
        for (var fileEntry : logFiles) {
            if (fileEntry.isDirectory()) {
                continue;
            }
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileEntry.getPath()))) { // opened file
                var lineIterator = reader.lines().iterator();
                var lines = new ArrayList<String>();
                StringBuilder line = new StringBuilder();
                while (lineIterator.hasNext()) {
                    var tmp = lineIterator.next();
                    if (tmp.startsWith(delimiter)) {
                        if (!line.toString().equals("")) {
                            lines.add(line.toString());
                        }
                        line = new StringBuilder(tmp.replace(delimiter, "")); // removing file
                    } else {
                        line.append("\n").append(tmp);
                    }
                }
                lines.add(line.toString());
                logs.addAll(lines);
            }
        }

        log.info("Finished reading logs");
        return logs;
    }

    private static List<File> getFileList(String logDirectoryPath) {
        List<File> logFiles;
        try {
            var folder = new File(logDirectoryPath);
            log.info(MessageFormat.format("Reading logs from {0}", folder.toURI()));
            if (!folder.isDirectory()) {
                throw new IllegalArgumentException("Given log directory is invalid, or is not a directory");
            }
            File[] logFilesTab = folder.listFiles();
            if (logFilesTab == null) {
                throw new IllegalArgumentException("Given log directory is empty");
            }
            logFiles = Arrays.asList(logFilesTab);
        } catch (Exception e) {
            log.error("Exception while reading log file directory: ", e);
            return Collections.emptyList();
        }
        logFiles.sort(Comparator.comparingLong(File::lastModified));
        return logFiles;
    }
}

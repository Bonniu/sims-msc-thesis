package mgr.potentialsi.logs;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class LogReader {

    public static List<Log> readLogs() throws IOException {
        var logDirectoryPath = "logs"; // todo properties
        var delimiter = "$";
        List<File> logFiles = getFileList(logDirectoryPath);
        if (logFiles == null) {
            log.info("Given log directory is invalid, or is not a directory, or log directory is empty");
            return new ArrayList<>();
        }
        var logs = new ArrayList<Log>();
        for (var fileEntry : logFiles) {
            if (fileEntry.isDirectory()) {
                continue;
            }
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileEntry.getPath()))) {
                var logsStr = reader.lines().toList().toString().split("\\$"); // todo fix reading every log
                for (var log : logsStr) {
                    logs.add(new Log(log));
                }
            }
        }

        log.info("Finished reading logs");
        return logs;
    }

    private static List<File> getFileList(String logDirectoryPath) {
        List<File> logFiles;
        try {
            var folder = new File(logDirectoryPath);
            log.info(MessageFormat.format("Reading logs from {0} ...", folder.toURI()));
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
            return null;
        }
        logFiles.sort((a, b) -> Long.compare(a.lastModified(), b.lastModified()));
        return logFiles;
    }
}

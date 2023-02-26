package mgr.sims.exceptionhandling;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionToEmailMessageParser {
    public static String parse(Exception e) {
        var buffer = new StringBuilder();
        buffer.append("Message: ");
        buffer.append(e.getMessage());
        buffer.append(" \n ");
        buffer.append("Stack trace: ");
        buffer.append(" \n ");
        var stackTrace = e.getStackTrace();
        for (var stackTraceElement : stackTrace) {
            buffer.append(stackTraceElement);
        }
        return buffer.toString();
    }
}

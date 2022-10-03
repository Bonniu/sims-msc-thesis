package mgr.potentialsi.exceptionhandling;

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

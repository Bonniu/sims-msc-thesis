package mgr.potentialsi.logs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LogList {
    private List<Log> logs;
    private int period;
}

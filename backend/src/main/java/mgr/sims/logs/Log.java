package mgr.sims.logs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "t_logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;
    private String dateTime;
    private String threadName;
    private LogLevel logLevel;
    private String classPath;
    private String username;

}

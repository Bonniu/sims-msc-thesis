package mgr.potentialsi.alerting.sender;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "mail.smtp")
public class SmtpProperties {
    private String username;
    private String password;
    private String host;
    private String port;
    private String sslTrust;
}

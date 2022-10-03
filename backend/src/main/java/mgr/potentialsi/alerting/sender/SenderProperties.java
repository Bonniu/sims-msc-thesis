package mgr.potentialsi.alerting.sender;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@ConfigurationProperties
public class SenderProperties {
    @Value("${mail.sender}")
    private String mailSender;
    @Value("${mail.receivers}")
    private String mailReceivers;
    @Value("${mail.smtp.username}")
    private String mailServerUserName;
    @Value("${mail.smtp.password}")
    private String mailServerPassword;
    @Value("${mail.smtp.host}")
    private String smtpHost;
    @Value("${mail.smtp.port}")
    private String smtpPort;
    @Value("${mail.smtp.ssl.trust}")
    private String sslTrust;
}

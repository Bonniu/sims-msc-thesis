package mgr.potentialsi.alerting.sender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "mail")
@EnableConfigurationProperties(SmtpProperties.class)
public class SenderProperties {

    private String sender;
    private String receivers;
    private final SmtpProperties smtpProperties;

}

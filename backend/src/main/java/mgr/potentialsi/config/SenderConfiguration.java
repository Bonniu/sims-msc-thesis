package mgr.potentialsi.config;

import lombok.RequiredArgsConstructor;
import mgr.potentialsi.alerting.sender.SenderProperties;
import org.apache.kafka.common.network.Send;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SenderProperties.class)
public class SenderConfiguration {

    private final SenderProperties properties;

    @Bean
    public Session mailSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", properties.getSmtpHost());
        prop.put("mail.smtp.port", properties.getSmtpPort());
        prop.put("mail.smtp.ssl.trust", properties.getSslTrust());
        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getMailServerUserName(), properties.getMailServerPassword());
            }
        });
    }
}

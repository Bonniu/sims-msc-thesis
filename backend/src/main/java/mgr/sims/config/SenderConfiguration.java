package mgr.sims.config;

import lombok.RequiredArgsConstructor;
import mgr.sims.alerting.sender.SenderProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({SenderProperties.class})
public class SenderConfiguration {

    private final SenderProperties properties;

    @Bean
    public Session mailSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", properties.getSmtpProperties().getHost());
        prop.put("mail.smtp.port", properties.getSmtpProperties().getPort());
        prop.put("mail.smtp.sslTrust", properties.getSmtpProperties().getSslTrust());
        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getSmtpProperties().getHost(), properties.getSmtpProperties().getPassword());
            }
        });
    }
}

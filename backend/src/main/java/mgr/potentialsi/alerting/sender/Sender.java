package mgr.potentialsi.alerting.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mgr.potentialsi.alerting.mail.model.Mail;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(SenderProperties.class)
public class Sender {

    private final SenderProperties properties;
    private final Session session;

    public String sendMail(Mail mail) {
        log.info("sending '" + mail + "' ...");
        try {
            send(mail);
        } catch (Exception ignored) {
            System.out.println();
        }
        return "SENT";
    }


    private void send(Mail mail) throws MessagingException {
        var message = new MimeMessage(session);
        message.setFrom(new InternetAddress(properties.getMailSender()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(properties.getMailReceivers()));
        message.setSubject(mail.getTopic());
        message.setContent(mail.getBody(), "text/html; charset=utf-8");
        Transport.send(message);
    }



}

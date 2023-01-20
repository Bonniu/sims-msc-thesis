package mgr.potentialsi.alerting.mail.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mgr.potentialsi.alerting.mail.MailService;
import mgr.potentialsi.alerting.mail.dto.SendMailEvent;
import mgr.potentialsi.alerting.mail.model.Mail;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailEventListener implements ApplicationListener<SendMailEvent> {

    private final MailService mailService;

    @Override
    public void onApplicationEvent(SendMailEvent event) {
        log.info("Received SendMailEvent - " + event.getMessage());
        try {
            mailService.sendMail(new Mail()); // TODO prepare and send mail
        } catch (Exception e) {
            log.error("Sending mail failed: ", e);
        }
    }
}
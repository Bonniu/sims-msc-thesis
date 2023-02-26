package mgr.sims.alerting.mail.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mgr.sims.alerting.mail.MailService;
import mgr.sims.alerting.mail.dto.SendMailEvent;
import mgr.sims.alerting.mail.model.Mail;
import mgr.sims.alerting.notification.NotificationRecipientService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailEventListener implements ApplicationListener<SendMailEvent> {

    private final MailService mailService;
    private final NotificationRecipientService notificationRecipientService;

    @Override
    public void onApplicationEvent(SendMailEvent event) {
        log.info("Received SendMailEvent");
        var notificationRecipients = notificationRecipientService.getNotificationRecipients();
        try {
            notificationRecipients.forEach(r -> mailService.sendMail(new Mail(event.getMessage(), r.getEmail())));
        } catch (Exception e) {
            log.error("Sending mail failed: ", e);
        }
    }
}
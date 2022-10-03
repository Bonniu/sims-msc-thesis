package mgr.potentialsi.alerting.mail;

import lombok.RequiredArgsConstructor;
import mgr.potentialsi.alerting.mail.model.Mail;
import mgr.potentialsi.alerting.mail.repository.MailRepository;
import mgr.potentialsi.alerting.sender.Sender;
import mgr.potentialsi.exceptionhandling.ExceptionToEmailMessageParser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailRepository mailRepository;
    private final Sender sender;

    public Mail sendMail(Mail mail) {
        var status = sender.sendMail(mail);
        mail.setStatus(status);
        return mailRepository.save(mail);
    }

}

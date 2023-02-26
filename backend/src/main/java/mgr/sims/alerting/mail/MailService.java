package mgr.sims.alerting.mail;

import lombok.RequiredArgsConstructor;
import mgr.sims.alerting.mail.model.Mail;
import mgr.sims.alerting.mail.repository.MailRepository;
import mgr.sims.alerting.sender.Sender;
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

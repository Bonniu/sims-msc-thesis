package mgr.potentialsi.alerting.sender;

import lombok.extern.slf4j.Slf4j;
import mgr.potentialsi.alerting.mail.model.Mail;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Sender {

    public String send(Mail mail) {
        log.info("sending '" + mail + "' ...");
        return "SENT";
    }

}

package mgr.potentialsi.alerting.mail.controller;

import lombok.RequiredArgsConstructor;
import mgr.potentialsi.alerting.mail.MailService;
import mgr.potentialsi.alerting.mail.dto.MailDTO;
import mgr.potentialsi.alerting.mail.mapper.MailEntityDTOMapper;
import mgr.potentialsi.alerting.mail.model.Mail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sender")
@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping
    public Mail sendMail(@RequestBody MailDTO mail) {
        return mailService.sendMail(MailEntityDTOMapper.toEntity(mail));
    }
}

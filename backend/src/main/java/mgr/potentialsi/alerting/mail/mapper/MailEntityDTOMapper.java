package mgr.potentialsi.alerting.mail.mapper;

import mgr.potentialsi.alerting.mail.dto.MailDTO;
import mgr.potentialsi.alerting.mail.model.Mail;

public class MailEntityDTOMapper {

    public static MailDTO toDto(Mail mail) {
        return MailDTO.builder().sentTo(mail.getSentTo()).body(mail.getBody()).build();
    }

    public static Mail toEntity(MailDTO dto) {
        return new Mail(dto.getBody(), dto.getSentTo());
    }
}

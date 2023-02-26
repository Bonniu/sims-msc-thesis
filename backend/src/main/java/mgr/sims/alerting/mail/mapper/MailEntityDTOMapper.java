package mgr.sims.alerting.mail.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mgr.sims.alerting.mail.dto.MailDTO;
import mgr.sims.alerting.mail.model.Mail;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailEntityDTOMapper {

    public static MailDTO toDto(Mail mail) {
        return MailDTO.builder().sentTo(mail.getSentTo()).body(mail.getBody()).build();
    }

    public static Mail toEntity(MailDTO dto) {
        return new Mail(dto.getBody(), dto.getSentTo());
    }
}

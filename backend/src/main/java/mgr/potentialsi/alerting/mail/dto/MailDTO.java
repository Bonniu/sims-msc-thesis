package mgr.potentialsi.alerting.mail.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class MailDTO {

    private Integer id;
    @NotNull
    private String body;
    private String topic;
    private String sentTo;
    private String status;
    private Date timestamp;
}

package mgr.potentialsi.alerting.mail.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class MailDTO {

    private Integer id;
    @NotNull
    private String body;
    @NotNull
    private String sentTo;
    private String status;
}

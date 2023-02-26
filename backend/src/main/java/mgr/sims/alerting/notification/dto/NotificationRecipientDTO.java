package mgr.sims.alerting.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRecipientDTO {

    private Integer id;
    private String email;

}

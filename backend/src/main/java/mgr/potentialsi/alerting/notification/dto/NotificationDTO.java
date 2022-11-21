package mgr.potentialsi.alerting.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDTO {
    private int id;
    private String message;
    private Date timestamp;
    private String errorType;
    private int channelId;
    private List<NotificationRecipientDTO> recipients;

}

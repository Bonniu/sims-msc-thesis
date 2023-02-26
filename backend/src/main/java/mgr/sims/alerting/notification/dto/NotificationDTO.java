package mgr.sims.alerting.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mgr.sims.alerting.notification.model.MessageType;

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
    private MessageType messageType;
    private int channelId;
    private List<NotificationRecipientDTO> recipients;
    private boolean seen;

}

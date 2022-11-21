package mgr.potentialsi.alerting.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mgr.potentialsi.alerting.notification.model.NotificationChannelType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationChannelDTO {
    private int id;
    private NotificationChannelType type;
    private boolean isActive;
}

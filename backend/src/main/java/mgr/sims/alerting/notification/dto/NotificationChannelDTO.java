package mgr.sims.alerting.notification.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mgr.sims.alerting.notification.mapper.NotificationChannelTypeDeserializer;
import mgr.sims.alerting.notification.mapper.NotificationChannelTypeSerializer;
import mgr.sims.alerting.notification.model.NotificationChannelType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationChannelDTO {
    private int id;
    @JsonDeserialize(using = NotificationChannelTypeDeserializer.class)
    @JsonSerialize(using = NotificationChannelTypeSerializer.class)
    private NotificationChannelType type;
    private boolean isActive;
}

package mgr.sims.alerting.notification.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mgr.sims.alerting.notification.dto.NotificationChannelDTO;
import mgr.sims.alerting.notification.model.NotificationChannel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationChannelEntityDtoMapper {

    public static NotificationChannel toEntity(NotificationChannelDTO dto) {
        return NotificationChannel.builder()
                .type(dto.getType())
                .isActive(dto.isActive())
                .build();
    }

    public static NotificationChannelDTO toDto(NotificationChannel notification) {
        return NotificationChannelDTO.builder()
                .id(notification.getId())
                .type(notification.getType())
                .isActive(notification.isActive())
                .build();
    }
}

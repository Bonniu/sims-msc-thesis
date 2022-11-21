package mgr.potentialsi.alerting.notification.mapper;

import mgr.potentialsi.alerting.notification.dto.NotificationChannelDTO;
import mgr.potentialsi.alerting.notification.model.NotificationChannel;

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

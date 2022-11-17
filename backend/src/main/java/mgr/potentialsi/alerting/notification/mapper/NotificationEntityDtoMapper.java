package mgr.potentialsi.alerting.notification.mapper;

import mgr.potentialsi.alerting.notification.dto.NotificationDTO;
import mgr.potentialsi.alerting.notification.model.Notification;

public class NotificationEntityDtoMapper {
    public static Notification toEntity(NotificationDTO dto) {
        return new Notification();
    }

    public static NotificationDTO toDto(Notification notification) {
        return new NotificationDTO(notification.getMessage());
    }
}

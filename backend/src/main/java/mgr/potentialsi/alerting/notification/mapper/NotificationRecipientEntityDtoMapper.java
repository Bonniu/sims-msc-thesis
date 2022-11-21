package mgr.potentialsi.alerting.notification.mapper;

import mgr.potentialsi.alerting.notification.dto.NotificationRecipientDTO;
import mgr.potentialsi.alerting.notification.model.NotificationRecipient;

public class NotificationRecipientEntityDtoMapper {
    public static NotificationRecipient toEntity(NotificationRecipientDTO dto) {
        return new NotificationRecipient(dto.getId(), dto.getEmail());
    }

    public static NotificationRecipient toEntityWithoutId(NotificationRecipientDTO dto) {
        return new NotificationRecipient(dto.getEmail());
    }

    public static NotificationRecipientDTO toDto(NotificationRecipient notification) {
        return new NotificationRecipientDTO(notification.getId(), notification.getEmail());
    }
}

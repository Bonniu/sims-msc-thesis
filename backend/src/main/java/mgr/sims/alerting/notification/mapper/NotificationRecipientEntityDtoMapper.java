package mgr.sims.alerting.notification.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mgr.sims.alerting.notification.dto.NotificationRecipientDTO;
import mgr.sims.alerting.notification.model.NotificationRecipient;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

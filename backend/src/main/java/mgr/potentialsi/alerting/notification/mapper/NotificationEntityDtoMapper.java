package mgr.potentialsi.alerting.notification.mapper;

import mgr.potentialsi.alerting.notification.dto.NotificationDTO;
import mgr.potentialsi.alerting.notification.model.Notification;
import mgr.potentialsi.alerting.notification.model.NotificationChannel;

public class NotificationEntityDtoMapper {
    public static Notification toEntity(NotificationDTO dto) {
        return Notification.builder()
                .channel(new NotificationChannel(dto.getChannelId()))
                .message(dto.getMessage())
                .messageType(dto.getMessageType())
                .timestamp(dto.getTimestamp())
                .recipients(dto.getRecipients().stream().map(NotificationRecipientEntityDtoMapper::toEntity).toList())
                .seen(dto.isSeen())
                .build();
    }

    public static NotificationDTO toDto(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .channelId(notification.getChannel().getId())
                .message(notification.getMessage())
                .messageType(notification.getMessageType())
                .timestamp(notification.getTimestamp())
                .recipients(notification.getRecipients().stream().map(NotificationRecipientEntityDtoMapper::toDto).toList())
                .seen(notification.isSeen())
                .build();
    }
}

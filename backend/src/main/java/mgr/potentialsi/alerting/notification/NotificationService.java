package mgr.potentialsi.alerting.notification;

import lombok.RequiredArgsConstructor;
import mgr.potentialsi.alerting.notification.model.MessageType;
import mgr.potentialsi.alerting.notification.model.Notification;
import mgr.potentialsi.alerting.notification.model.NotificationChannel;
import mgr.potentialsi.alerting.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationChannelService notificationChannelService;

    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotification(Integer id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Notification with id {0} not found", id)));
    }

    @Transactional
    public void addNotification(String message, MessageType messageType) {
        List<NotificationChannel> activeNotificationChannels = notificationChannelService.getActiveNotificationChannels();
        for (var channel : activeNotificationChannels) {
            var notification = Notification
                    .builder()
                    .seen(false)
                    .channel(channel)
                    .message(message)
                    .messageType(messageType)
                    .timestamp(new Date())
                    .build();
            notificationRepository.save(notification);
        }

    }

    public void addNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public void deleteNotification(Integer id) {
        notificationRepository.deleteById(id);
    }

    public void modifyNotification(Integer id, Notification notification) {
        notification.setId(id);
        notificationRepository.save(notification);
    }

}

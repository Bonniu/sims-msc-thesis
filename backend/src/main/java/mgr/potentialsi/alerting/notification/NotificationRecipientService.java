package mgr.potentialsi.alerting.notification;

import lombok.RequiredArgsConstructor;
import mgr.potentialsi.alerting.notification.model.NotificationRecipient;
import mgr.potentialsi.alerting.notification.repository.NotificationRecipientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationRecipientService {

    private final NotificationRecipientRepository notificationRecipientRepository;

    public List<NotificationRecipient> getNotificationRecipients() {
        return notificationRecipientRepository.findAll();
    }

    public NotificationRecipient getNotificationRecipient(Integer id) {
        return notificationRecipientRepository.findById(id).orElseThrow();
    }

    public void addNotificationRecipient(NotificationRecipient channel) {
        notificationRecipientRepository.save(channel);
    }

    public void deleteNotificationRecipient(Integer channelId) {
        notificationRecipientRepository.deleteById(channelId);
    }


}

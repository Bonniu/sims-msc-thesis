package mgr.potentialsi.alerting.notification;

import lombok.RequiredArgsConstructor;
import mgr.potentialsi.alerting.notification.model.NotificationChannel;
import mgr.potentialsi.alerting.notification.repository.NotificationChannelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationChannelService {

    private final NotificationChannelRepository notificationChannelRepository;

    public List<NotificationChannel> getActiveNotificationChannels() {
        return notificationChannelRepository.getNotificationChannelsByIsActiveTrue();
    }

    public NotificationChannel getNotificationChannel(Integer id) {
        return notificationChannelRepository.findById(id).orElseThrow();
    }

    public List<NotificationChannel> getNotificationChannels() {
        return notificationChannelRepository.findAll();
    }

    public void addNotificationChannel(NotificationChannel channel) {
        notificationChannelRepository.save(channel);
    }

    public void deleteNotificationChannel(Integer channelId) {
        notificationChannelRepository.deleteById(channelId);
    }

}

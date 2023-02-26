package mgr.potentialsi.alerting.notification;

import lombok.RequiredArgsConstructor;
import mgr.potentialsi.alerting.notification.dto.NotificationChannelDTO;
import mgr.potentialsi.alerting.notification.model.NotificationChannel;
import mgr.potentialsi.alerting.notification.repository.NotificationChannelRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
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

    @Transactional
    public void saveNotificationChannels(List<NotificationChannelDTO> channels) {
        List<NotificationChannel> allChannels = notificationChannelRepository.findAll();
        var currentChannels = allChannels.stream().map(NotificationChannel::getId).toList();
        var updatedChannels = channels.stream().map(NotificationChannelDTO::getId).toList();
        if (!new HashSet<>(currentChannels).containsAll(updatedChannels)) {
            throw new IllegalArgumentException("Given notification channels not found!");
        }
        for (var channel : allChannels) {
            NotificationChannelDTO notificationChannelDTO = channels.stream().filter(c -> c.getId() == channel.getId()).findFirst().orElseThrow();
            channel.setActive(notificationChannelDTO.isActive());
            notificationChannelRepository.save(channel);
        }
    }

}

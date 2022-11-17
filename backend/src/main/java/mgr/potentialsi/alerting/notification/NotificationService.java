package mgr.potentialsi.alerting.notification;

import lombok.RequiredArgsConstructor;
import mgr.potentialsi.alerting.notification.model.Notification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    public List<Notification> getNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification("XD"));
        return notifications;
    }
}

package mgr.sims.alerting.notification.repository;

import mgr.sims.alerting.notification.model.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationChannelRepository extends JpaRepository<NotificationChannel, Integer> {
    @SuppressWarnings("SpringDataMethodInconsistencyInspection")
    public List<NotificationChannel> getNotificationChannelsByIsActiveTrue();
}

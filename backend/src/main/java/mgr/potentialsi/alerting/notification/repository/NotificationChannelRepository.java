package mgr.potentialsi.alerting.notification.repository;

import mgr.potentialsi.alerting.notification.model.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationChannelRepository extends JpaRepository<NotificationChannel, Integer> {
}

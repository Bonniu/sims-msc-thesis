package mgr.potentialsi.alerting.notification.repository;

import mgr.potentialsi.alerting.notification.model.NotificationRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Integer> {
}

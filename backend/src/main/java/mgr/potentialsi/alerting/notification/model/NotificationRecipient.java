package mgr.potentialsi.alerting.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "t_notification_mail_recipients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;

    public NotificationRecipient(String email) {
        this.email = email;
    }
}

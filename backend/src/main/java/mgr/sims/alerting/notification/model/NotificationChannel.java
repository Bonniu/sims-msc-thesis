package mgr.sims.alerting.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "t_notification_channels")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private NotificationChannelType type;
    private boolean isActive;

    public NotificationChannel(int id) {
        this.id = id;
    }
}

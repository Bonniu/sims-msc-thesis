package mgr.sims.alerting.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "t_notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String message;
    private Date timestamp;
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    @OneToOne
    private NotificationChannel channel;
    @ManyToMany(cascade = {CascadeType.DETACH})
    @JoinTable(
            name = "t_notifications_recipients",
            joinColumns = {@JoinColumn(name = "notification_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipient_id")}
    )
    private List<NotificationRecipient> recipients;
    private boolean seen;

}

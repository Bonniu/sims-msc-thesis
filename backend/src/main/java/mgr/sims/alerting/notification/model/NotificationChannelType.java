package mgr.sims.alerting.notification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationChannelType {
    MAIL("Email"),
    FRONT("Dashboard");

    private final String type;

}

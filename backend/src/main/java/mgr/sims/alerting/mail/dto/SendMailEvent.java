package mgr.sims.alerting.mail.dto;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SendMailEvent extends ApplicationEvent {
    private final String message;

    public SendMailEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
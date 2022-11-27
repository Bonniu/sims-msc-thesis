package mgr.potentialsi.alerting.mail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Entity(name = "t_mails")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String topic;
    private String body;
    private String sentTo;
    private String status;
    private Date timestamp;

    public Mail(String body, String sentTo) {
        this(null, "administrator", body, sentTo, null, new Date());
    }

}

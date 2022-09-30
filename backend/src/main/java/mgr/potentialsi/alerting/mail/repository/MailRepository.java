package mgr.potentialsi.alerting.mail.repository;

import mgr.potentialsi.alerting.mail.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends JpaRepository<Mail, Integer> {

}

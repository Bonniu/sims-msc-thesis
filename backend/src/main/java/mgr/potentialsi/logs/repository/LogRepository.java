package mgr.potentialsi.logs.repository;

import mgr.potentialsi.logs.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
}

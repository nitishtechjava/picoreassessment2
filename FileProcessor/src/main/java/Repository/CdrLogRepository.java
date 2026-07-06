package Repository;

import Entity.CDRLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdrLogRepository
        extends JpaRepository<CDRLogEntity, Long> {

}
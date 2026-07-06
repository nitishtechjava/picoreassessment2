package Repository;

import Entity.CDRLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CdrLogRepository
        extends JpaRepository<CDRLogEntity, Long> {

}
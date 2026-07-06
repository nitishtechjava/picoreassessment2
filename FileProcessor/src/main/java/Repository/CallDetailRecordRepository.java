package Repository;

import Entity.CallDetailRecord;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CallDetailRecordRepository
        extends JpaRepository<CallDetailRecord, Long> {

}
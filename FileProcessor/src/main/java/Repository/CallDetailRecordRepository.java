package Repository;

import Entity.CallDetailRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallDetailRecordRepository
        extends JpaRepository<CallDetailRecord, Long> {

}
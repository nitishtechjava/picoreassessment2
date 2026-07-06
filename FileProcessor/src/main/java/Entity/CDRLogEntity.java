package Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cdr_logs")
@Getter
@Setter
@NoArgsConstructor
public class CDRLogEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "file_name", nullable = false)
        private String fileName;

        @Column(name = "upload_start_time", nullable = false)
        private LocalDateTime uploadStartTime;

        @Column(name = "upload_end_time")
        private LocalDateTime uploadEndTime;

        @Column(name = "total_records")
        private Integer totalRecords;

        @Column(name = "success_records")
        private Integer successRecords;

        @Column(name = "failed_records")
        private Integer failedRecords;

        @Column(name = "status", nullable = false, length = 20)
        private String status;

        @Column(name = "remarks")
        private String remarks;

        @Column(name = "created_at", insertable = false, updatable = false)
        private LocalDateTime createdAt;
}

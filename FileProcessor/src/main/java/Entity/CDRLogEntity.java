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

        private String fileName;

        private LocalDateTime uploadStartTime;

        private LocalDateTime uploadEndTime;

        private Integer successRecords;

        private Integer failedRecords;

        private String status;

}

package Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cdr_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

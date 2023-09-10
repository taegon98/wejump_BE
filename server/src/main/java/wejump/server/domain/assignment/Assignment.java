package wejump.server.domain.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Getter
@Table(name = "assignment")
@Builder
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    /*
     ****************************************비지니스 로직****************************************
     */

    public void updateAssignment(String title, String description, LocalDateTime startDate, LocalDateTime dueDate) {
        if (title != null) {
            this.title = title;
        }
        if (description != null) {
            this.description = description;
        }
        if (startDate != null) {
            this.startDate = startDate;
        }
        if (dueDate != null) {
            this.dueDate = dueDate;
        }
    }

}

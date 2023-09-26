package wejump.server.domain.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.api.dto.assignment.AssignmentResponseDTO;
import wejump.server.api.dto.course.CourseResponseDTO;
import wejump.server.domain.course.Course;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDate startDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Submit> submits; // Assignment와 Submit 엔티티 간의 관계 설정


    public AssignmentResponseDTO build(Assignment assignment) {

        return AssignmentResponseDTO.builder()
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .startDate(assignment.getStartDate())
                .dueDate(assignment.getDueDate())
                .build();
    }


    /*
     ****************************************비지니스 로직****************************************
     */

    //dto -> model로 변환 기능 포함
    public void updateAssignment(String title, String description, LocalDate startDate, LocalDate dueDate) {
            this.title = title;
            this.description = description;
            this.startDate = startDate;
            this.dueDate = dueDate;
    }
}

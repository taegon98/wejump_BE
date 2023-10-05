package wejump.server.domain.course;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@DynamicUpdate
@Entity
@Table(name = "course_plan")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoursePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    @Column(name = "week", nullable = false)
    private Integer week;

    @Column(name = "title", nullable = false)
    private String title;

    @Builder.Default
    @Column(name = "video", nullable = false)
    private Boolean video = false;

    @Builder.Default
    @Column(name = "assignment", nullable = false)
    private Boolean assignment = false;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}

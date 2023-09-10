package wejump.server.domain.course;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@DynamicUpdate
@Entity
@Getter
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quota", nullable = false)
    private Integer quota;

    @Column(name = "start", nullable = false)
    private String start_date;

    @Column(name = "end", nullable = false)
    private String end_date;

    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Column(name = "summary", length = 100, nullable = false)
    private String summary;

    @Column(name = "reference", nullable = true)
    private String reference;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnrollCourse> enrolledCourses = new ArrayList<>();
}

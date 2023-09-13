package wejump.server.domain.lesson;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.domain.course.Course;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DynamicUpdate
@Entity
@Table(name = "lesson")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long id;

    @Column(nullable = false)
    private Integer week;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate start;

    @JsonBackReference
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attend> inCourses = new ArrayList<>();

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "course_id")
    private Course course;

    public void updateLessonInfo(Integer week, String name, String content, LocalDate start) {
        this.week = week;
        this.name = name;
        this.content = content;
        this.start = start;
    }

}

package wejump.server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, unique = true)
    private int week;

    @Getter
    @Column(length = 45, nullable = false)
    private String lesson_name;

    @Getter
    @Column(length = 256, nullable = false)
    private String content;

    @Getter
    private LocalDate lesson_date;

    @Getter
    @OneToMany(mappedBy = "lesson")
    private List<Attend> attends = new ArrayList<>();

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public void setWeek(int week) {
        this.week = week;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLesson_time(LocalDate lesson_date) {
        this.lesson_date = lesson_date;
    }

    public void setAttends(List<Attend> attends) {
        this.attends = attends;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

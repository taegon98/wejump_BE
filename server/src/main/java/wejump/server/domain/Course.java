package wejump.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(length = 45, nullable = false)
    private String course_name;

    @Getter
    private int enrollment_limit;

    @Getter
    @Column(nullable = false)
    private LocalDate start_date;

    @Getter
    @Column(nullable = false)
    private LocalDate end_date;

    @Getter
    @Column(length = 255, nullable = false)
    private String description;

    @Getter
    @Column(length = 255, nullable = false)
    private String summary;

    @Getter
    @Column(length = 45)
    private String reference;

    @Getter
    @JsonBackReference
    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setEnrollment_limit(int enrollment_limit) {
        this.enrollment_limit = enrollment_limit;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}

package wejump.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DynamicUpdate
@Entity
@Table(name = "course")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDate start_date;

    @Column(name = "end", nullable = false)
    private LocalDate end_date;

    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Column(name = "summary", length = 100, nullable = false)
    private String summary;

    @Column(name = "reference", nullable = true)
    private String reference;

    @JsonBackReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lesson> lessons = new ArrayList<>();
}

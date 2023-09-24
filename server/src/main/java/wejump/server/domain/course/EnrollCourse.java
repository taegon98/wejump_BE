package wejump.server.domain.course;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.domain.member.Member;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Getter
@Table(name = "enroll_course")
@Builder
public class EnrollCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enroll_id")
    private Long id;

    @Column(name = "date", nullable = true)
    private String date;

    @Column(name = "instructor", nullable = true)
    private Boolean instructor;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "member_id") // Member 엔티티의 외래 키
    private Member member;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "course_id") // Course 엔티티의 외래 키
    private Course course;

    /*
     ****************************************비지니스 로직****************************************
     */
}

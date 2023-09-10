package wejump.server.domain.course;

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

    @Column(name = "instructor", length = 50, nullable = true)
    private String instructor;

    @ManyToOne
    @JoinColumn(name = "member_id") // Member 엔티티의 외래 키
    private Member member;

    @ManyToOne
    @JoinColumn(name = "course_id") // Course 엔티티의 외래 키
    private Course course;

    /*
     ****************************************비지니스 로직****************************************
     */
}

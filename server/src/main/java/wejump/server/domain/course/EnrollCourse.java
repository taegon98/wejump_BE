package wejump.server.domain.course;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.domain.member.Member;

import javax.persistence.*;

@NoArgsConstructor
@DynamicUpdate
@Entity
@Getter
@Table(name = "enroll_course")
public class EnrollCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enroll_id")
    private Long id;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "instructor", length = 50, nullable = false)
    private String instructor;

    @ManyToOne
    @JoinColumn(name = "member_id") // Member 엔티티의 외래 키
    private Member member;

    @ManyToOne
    @JoinColumn(name = "course_id") // Course 엔티티의 외래 키
    private Course course;
}

package wejump.server.domain.lesson;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.domain.member.Member;

import javax.persistence.*;

@DynamicUpdate
@Entity
@Table(name = "attend")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attend_id")
    private Long id;

    @Column
    private Boolean attendance;

    @Column
    private Boolean assignment;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateStatusInfo(Boolean attendance, Boolean assignment) {
        this.attendance = attendance;
        this.assignment = assignment;
    }
}

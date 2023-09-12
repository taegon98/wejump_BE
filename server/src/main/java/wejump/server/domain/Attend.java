package wejump.server.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@DynamicUpdate
@Entity
@Table(name = "attend")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attend_id")
    private Long id;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    private User user;

    public void updateAttendInfo(String status) {
        this.status = status;
    }
}

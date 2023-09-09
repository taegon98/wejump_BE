package wejump.server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
public class Attend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false)
    private String attendance_status;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setAttendance_status(String attendance_status) {
        this.attendance_status = attendance_status;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Attend() {
        this.attendance_status = "unknown"; // 기본값 설정
    }
}

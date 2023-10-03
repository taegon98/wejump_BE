package wejump.server.domain.assignment;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import wejump.server.domain.member.Member;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "submit")
public class Submit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "file_path") // 파일 경로
    private String filePath;

    @Column(name = "submission_time", nullable = false)
    private LocalDateTime submissionTime;

    @Column(name = "comment")
    private String comment;
}

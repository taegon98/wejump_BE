package wejump.server.domain.announcement;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import wejump.server.api.dto.announcement.AnnouncementRequestDTO;
import wejump.server.domain.course.Course;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
@Table(name="notice")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnotice")
    private Long announcementId;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="announcement_date")
    private LocalDateTime announcementDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_idcourse")
    private Course course;

    /*@Column(name="course_idcourse")
    private Long courseId;*/

    public void patch(AnnouncementRequestDTO dto) {

        if(dto.getTitle() != null){
            this.title = dto.getTitle();
        }
        if(dto.getContent() != null) {
            this.content = dto.getContent();
        }
    }

    public static Announcement createAnnouncement(AnnouncementRequestDTO dto, Course course) {
        // 예외 발생
        if (dto.getAnnouncementId() != null)
            throw new IllegalArgumentException("공지 생성 실패! 공지의 id가 없어야 합니다.");
        // 엔티티 생성 및 반환
        return Announcement.builder().
                announcementId(dto.getAnnouncementId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .announcementDate(dto.getAnnouncementDate())
                .course(course)
                .build();
    }
}

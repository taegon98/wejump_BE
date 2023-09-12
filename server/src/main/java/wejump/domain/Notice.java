package wejump.domain;

import lombok.*;
import wejump.api.dto.Notice.NoticeResponseDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnotice")
    private Long noticeId;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="announcement_date")
    private LocalDateTime announcementDate;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="course_idcourse")
    //private Course course;

    @Column(name="course_idcourse")
    private Long courseId;

    public void patch(Notice notice) {
        if(notice.title != null){
            this.title = notice.title;
        }
        if(notice.content != null){
            this.content = notice.content;
        }
        //글을 수정한 시간 정보가 필요할까요? 게시시간 정보를 바꾸면 안 될 것 같아서...
    }

    public NoticeResponseDto toResponseDto() {
        return NoticeResponseDto.builder()
                .noticeId(noticeId)
                .title(title)
                .content(content)
                .announcementDate(announcementDate)
                .courseId(courseId)
                .build();
    }
}

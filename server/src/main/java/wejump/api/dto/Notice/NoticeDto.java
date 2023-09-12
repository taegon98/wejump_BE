package wejump.api.dto.Notice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wejump.domain.Notice;
import lombok.Getter;
import java.time.LocalDateTime;

@Slf4j
@Getter
@AllArgsConstructor

public class NoticeDto {

    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime announcementDate;
    private Long courseId;

    //create 용
    public Notice toNotice(Long courseId) {
        log.info("transform_create");
        return Notice.builder()
                .noticeId(noticeId)
                .title(title)
                .content(content)
                .announcementDate(announcementDate = LocalDateTime.now())
                .courseId(courseId)
                .build();
    }

    //update 용
    public Notice toNotice() {
        log.info("transform_update");
        return Notice.builder()
                .noticeId(noticeId)
                .title(title)
                .content(content)
                .announcementDate(announcementDate = LocalDateTime.now())
                .courseId(courseId)
                .build();
    }

}

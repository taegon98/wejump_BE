package wejump.api.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

    public Notice toNotice() {
        return Notice.builder()
                .noticeId(noticeId)
                .title(title)
                .content(content)
                .announcementDate(announcementDate = LocalDateTime.now())
                .courseId(courseId)
                .build();
    }

}

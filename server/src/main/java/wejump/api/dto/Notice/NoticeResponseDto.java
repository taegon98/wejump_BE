package wejump.api.dto.Notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import java.time.LocalDateTime;

@Slf4j
@Getter
@Builder
public class NoticeResponseDto {

    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime announcementDate;
    private Long courseId;

}

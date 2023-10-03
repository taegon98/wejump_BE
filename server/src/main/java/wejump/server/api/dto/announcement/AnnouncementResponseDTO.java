package wejump.server.api.dto.announcement;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import wejump.server.domain.announcement.Announcement;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Builder
public class AnnouncementResponseDTO {

    private Long announcementId;
    private String title;
    private String content;
    private LocalDateTime announcementDate;
    private Long courseId;

    public static AnnouncementResponseDTO createAnnouncementResponseDto(Announcement announcement) {
        return AnnouncementResponseDTO.builder()
                .announcementId(announcement.getAnnouncementId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .announcementDate(announcement.getAnnouncementDate())
                .courseId(announcement.getCourse().getId())
                .build();
    }
}

package wejump.server.api.dto.lesson;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class LessonResponseDTO {
    private Long id;
    private Integer week;
    private LocalDate start;
    private String name;
    private String content;
}

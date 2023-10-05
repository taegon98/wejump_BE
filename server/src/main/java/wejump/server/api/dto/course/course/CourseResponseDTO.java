package wejump.server.api.dto.course.course;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CourseResponseDTO {
    private String name;
    private Integer quota;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String summary;
    private String reference;
    private String image;
}

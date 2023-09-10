package wejump.server.api.dto.course;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseResponseDTO {
    private String name;
    private Integer quota;
    private String startDate;
    private String endDate;
    private String description;
    private String summary;
    private String reference;
}

package wejump.server.api.dto.course.course;

import lombok.Builder;
import lombok.Getter;
import wejump.server.domain.lesson.Lesson;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

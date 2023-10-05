package wejump.server.api.dto.course.courseInfo;

import lombok.Builder;
import lombok.Getter;
import wejump.server.api.dto.course.assignment.AssignmentResponseDTO;

import java.time.LocalDate;

@Getter
@Builder
public class CourseInfoResponseDTO {

    private Integer week;

    private LocalDate start;

    private String video;

    private AssignmentResponseDTO assignmentResponseDTO;
}

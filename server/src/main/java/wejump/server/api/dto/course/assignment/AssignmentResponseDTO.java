package wejump.server.api.dto.course.assignment;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class AssignmentResponseDTO {

    private String title;

    private String description;

    private LocalDate end;
}

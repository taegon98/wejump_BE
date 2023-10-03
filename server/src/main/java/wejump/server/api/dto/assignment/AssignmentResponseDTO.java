package wejump.server.api.dto.assignment;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class AssignmentResponseDTO {

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate dueDate;
}

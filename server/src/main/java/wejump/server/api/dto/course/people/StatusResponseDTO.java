package wejump.server.api.dto.course.people;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class StatusResponseDTO {
    private Integer week;
    private List<StatusDTO> statusByWeek;
}

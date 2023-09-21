package wejump.server.api.dto.syllabus;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SyllabusDTO {
    private String summary;
    private String reference;
    private List<PlanDTO> plans;
}

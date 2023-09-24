package wejump.server.api.dto.syllabus;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
public class SyllabusDTO {
    @NotNull(message = "summary는 필수입니다.")
    private String summary;

    @NotNull(message = "reference는 필수입니다.")
    private String reference;


    private List<PlanDTO> plans;
}

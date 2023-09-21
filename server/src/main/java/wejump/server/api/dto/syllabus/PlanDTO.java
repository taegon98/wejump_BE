package wejump.server.api.dto.syllabus;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PlanDTO {

    @NotNull(message = "week는 필수 입니다.")
    private Integer week;

    private String title;
    private Boolean video;
    private Boolean assignment;
}

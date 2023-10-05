package wejump.server.api.dto.course.syllabus;


import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PlanDTO {

    @NotNull(message = "week는 필수 입니다.")
    private Integer week;

    @NotNull(message = "title은 필수입니다.")
    private String title;

    private Boolean video;

    private Boolean assignment;
}

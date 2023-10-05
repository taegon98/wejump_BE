package wejump.server.api.dto.course.people;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class StatusRequestDTO {

    @NotNull(message = "id는 필수입니다.")
    private Long id;

    @NotNull(message = "attendance는 필수입니다.")
    private Boolean attendance;

    @NotNull(message = "assignment는 필수입니다.")
    private Boolean assignment;
}

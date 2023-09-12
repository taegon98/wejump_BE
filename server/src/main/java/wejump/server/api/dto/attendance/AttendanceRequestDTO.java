package wejump.server.api.dto.attendance;

import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class AttendanceRequestDTO {

    @NotNull(message = "id는 필수입니다.")
    private Long id;

    @NotBlank(message = "status는 필수입니다.")
    private String status;
}

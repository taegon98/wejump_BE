package wejump.server.api.dto.assignment;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class AssignmentRequestDTO {
    @NotBlank(message = "과제 이름은 필수 입력 항목입니다.")
    private String title;

    @NotBlank(message = "과제 내용은 필수 입력 항목입니다.")
    private String description;

    @NotNull(message = "과제 시작 날짜는 필수 입력 항목입니다.")
    private LocalDate startDate;

    @NotNull(message = "과제 종료 날짜는 필수 입력 항목입니다.")
    private LocalDate endDate;

}

package wejump.server.api.dto.lesson;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
public class LessonRequestDTO {
    private Long id;

    @NotNull(message = "week는 필수입니다.")
    @Positive(message = "week는 양수이어야 합니다.")
    private Integer week;

    @NotNull(message = "lesson 날짜는 필수입니다.")
    private LocalDate start;

    @NotBlank(message = "lesson name은 필수입니다.")
    private String name;

    @NotBlank(message = "lesson 설명은 필수입니다.")
    private String content;
}

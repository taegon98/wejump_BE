package wejump.server.api.dto.attendance;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class AttendanceResponseDTO {

    private Long id;
    private String name;
    private LocalDate date;
    private String status;
}

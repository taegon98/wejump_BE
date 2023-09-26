package wejump.server.api.dto.status;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatusResponseDTO {
    private Long id;
    private String name;
    private String date;
    private Boolean attendance;
    private Boolean assignment;
}

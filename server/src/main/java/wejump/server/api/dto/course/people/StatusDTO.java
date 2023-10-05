package wejump.server.api.dto.course.people;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StatusDTO {
    private String name;
    private List<Object> statusByName;
}

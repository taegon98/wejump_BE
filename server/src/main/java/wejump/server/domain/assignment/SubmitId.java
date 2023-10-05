package wejump.server.domain.assignment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SubmitId implements Serializable {
    @Column(name = "assignment_id")
    private Long assignmentId;

    @Column(name = "member_id")
    private Long memberId;
}

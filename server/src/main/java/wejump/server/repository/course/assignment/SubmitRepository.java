package wejump.server.repository.course.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wejump.server.domain.assignment.Submit;
import wejump.server.domain.assignment.SubmitId;

import java.util.List;

@Repository
public interface SubmitRepository extends JpaRepository<Submit, SubmitId> {
}
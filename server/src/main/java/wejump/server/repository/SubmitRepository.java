package wejump.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wejump.server.domain.assignment.Submit;

@Repository
public interface SubmitRepository extends JpaRepository<Submit, Long> {
}

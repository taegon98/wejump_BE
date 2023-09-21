package wejump.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wejump.server.domain.assignment.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}

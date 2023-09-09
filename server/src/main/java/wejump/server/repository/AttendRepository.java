package wejump.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wejump.server.domain.Attend;

@Repository
public interface AttendRepository extends JpaRepository<Attend, Long> {
}

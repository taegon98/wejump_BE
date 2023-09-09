package wejump.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wejump.server.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

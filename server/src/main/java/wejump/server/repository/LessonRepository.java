package wejump.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wejump.server.domain.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}

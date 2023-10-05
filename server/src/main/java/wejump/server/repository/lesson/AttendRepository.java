package wejump.server.repository.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wejump.server.domain.lesson.Attend;

import java.util.List;

@Repository
public interface AttendRepository extends JpaRepository<Attend, Long> {
    List<Attend> findAllByLessonIdOrderByName(Long lessonId);
}

package wejump.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wejump.server.domain.lesson.Lesson;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourse_Id(Long courseId);
    Lesson findByCourseIdAndStart(Long courseId, LocalDate start);
}

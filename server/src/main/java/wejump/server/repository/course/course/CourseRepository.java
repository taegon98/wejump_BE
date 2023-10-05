package wejump.server.repository.course.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wejump.server.domain.course.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}

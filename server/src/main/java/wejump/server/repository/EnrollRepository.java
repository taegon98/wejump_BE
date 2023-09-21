package wejump.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wejump.server.domain.course.EnrollCourse;

import java.util.List;

@Repository
public interface EnrollRepository extends JpaRepository<EnrollCourse, Long> {
    List<EnrollCourse> findAllByCourseId(Long courseId);
}

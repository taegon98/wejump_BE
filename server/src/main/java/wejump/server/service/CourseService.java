package wejump.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wejump.server.domain.Course;
import wejump.server.repository.CourseRepository;

import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    //id로 course 정보 가져오기
    public Course getCourseById(Long courseId){
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            return course;
        }
        System.out.println("객체를 찾을 수 없습니다.");
        return null;
    }
}

package wejump.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wejump.server.domain.Course;
import wejump.server.domain.Lesson;
import wejump.server.dto.LessonDTO;
import wejump.server.dto.SyllabusDTO;
import wejump.server.repository.CourseRepository;
import wejump.server.repository.LessonRepository;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
    }

    //id로 course 정보 가져오기
    public Course getCourseById(Long courseId){
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            return course;
        }
        System.out.println("Cannot find Course");
        return null;
    }

    //syllabus create, update, delete
    @Transactional
    public Course updateCourse(Long courseId, SyllabusDTO syllabusDTO){
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Cannot find Course"));

        //update course
        existingCourse.setSummary(syllabusDTO.getSummary());
        existingCourse.setReference(syllabusDTO.getReference());

        //convert SyllabusDTO lessons to Lesson List
        List<Lesson> updatedLessons = LessonDTO.toLessons(existingCourse,syllabusDTO.getLessons());

        for (Lesson updatedLesson: updatedLessons){
            Long lessonId = updatedLesson.getId();
            if (lessonId != null){
                Optional<Lesson> existingLesson = lessonRepository.findById(lessonId);
                if(existingLesson.isPresent()){
                    lessonRepository.deleteById(updatedLesson.getId());
                    lessonRepository.flush();
                }
            }
            if (updatedLesson.getWeek() == -1){
                continue;
            }
            else{
                //updatedLesson.setId(null);
                lessonRepository.save(updatedLesson);
                existingCourse.getLessons().add(updatedLesson);
            }
        }

        return courseRepository.save(existingCourse);
    }
}

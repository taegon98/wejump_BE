package wejump.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wejump.server.domain.Course;
import wejump.server.domain.Lesson;
import wejump.server.dto.LessonDTO;
import wejump.server.dto.SyllabusDTO;
import wejump.server.service.CourseService;
import wejump.server.service.LessonService;

import javax.transaction.Transactional;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/syllabus")
public class SyllabusController {
    private final CourseService courseService;
    private final LessonService lessonService;

    @Autowired
    public SyllabusController(CourseService courseService, LessonService lessonService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
    }

    @GetMapping("/{courseId}")
    public SyllabusDTO getSyllabus(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        List<Lesson> lessons = lessonService.getLessonByCourseId((courseId));

        SyllabusDTO syllabusDTO = new SyllabusDTO();
        syllabusDTO.setSummary(course.getSummary());
        syllabusDTO.setReference(course.getReference());

        if (lessons == null) {
            syllabusDTO.setLessons(new ArrayList<>());
        }
        else{
            syllabusDTO.setLessons(mapToLessonDTOs(lessons));
        }

        return syllabusDTO;
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourseSyllabus(
            @PathVariable Long courseId,
            @RequestBody SyllabusDTO syllabusDTO){
        Course updatedCourse = courseService.updateCourse(courseId, syllabusDTO);

        return ResponseEntity.ok(updatedCourse);
    }

    private List<LessonDTO> mapToLessonDTOs(List<Lesson> lessons){
        return lessons.stream()
                .map(lesson ->{
                    LessonDTO lessonDTO = new LessonDTO();
                    lessonDTO.setId(lesson.getId());
                    lessonDTO.setWeek(lesson.getWeek());
                    lessonDTO.setLessonDate(lesson.getLesson_date());
                    lessonDTO.setLessonName(lesson.getLesson_name());
                    lessonDTO.setContent(lesson.getContent());
                    return lessonDTO;
                })
                .collect(Collectors.toList());
    }
}

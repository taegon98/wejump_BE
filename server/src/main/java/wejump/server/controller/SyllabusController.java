package wejump.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wejump.server.domain.Course;
import wejump.server.domain.Lesson;
import wejump.server.dto.LessonDTO;
import wejump.server.dto.SyllabusDTO;
import wejump.server.service.CourseService;
import wejump.server.service.LessonService;

import java.sql.SQLOutput;
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

    @GetMapping("/{idcourse}")
    public SyllabusDTO getSyllabus(@PathVariable Long idcourse) {
        Course course = courseService.getCourseById(idcourse);
        List<Lesson> lessons = lessonService.getLessonByCourseId((idcourse));

        SyllabusDTO syllabusDTO = new SyllabusDTO();
        syllabusDTO.setSummary(course.getSummary());
        syllabusDTO.setReference(course.getReference());
        syllabusDTO.setLessons(mapToLessonDTOs(lessons));

        return syllabusDTO;
    }

    private List<LessonDTO> mapToLessonDTOs(List<Lesson> lessons){
        return lessons.stream()
                .map(lesson ->{
                    LessonDTO lessonDTO = new LessonDTO();
                    lessonDTO.setWeek(lesson.getWeek());
                    lessonDTO.setLessonName(lesson.getLesson_name());
                    lessonDTO.setContent(lesson.getContent());
                    return lessonDTO;
                })
                .collect(Collectors.toList());
    }
}

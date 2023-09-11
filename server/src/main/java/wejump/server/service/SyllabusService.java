package wejump.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import wejump.server.api.dto.lesson.LessonRequestDTO;
import wejump.server.domain.Course;
import wejump.server.domain.Lesson;
import wejump.server.api.dto.lesson.LessonResponseDTO;
import wejump.server.api.dto.SyllabusDTO;
import wejump.server.repository.CourseRepository;
import wejump.server.repository.LessonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SyllabusService {
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    @Transactional(readOnly = true)
    public SyllabusDTO getSyllabusById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find course"));

        List<Lesson> lessons = lessonRepository.findByCourse_Id(courseId);

        // Lesson 객체를 LessonDTO로 변환
        List<LessonResponseDTO> lessonResponseDTOS = lessons.stream()
                .map(this::createLessonDTO)
                .collect(Collectors.toList());
        return createSyllabusDTO(course, lessonResponseDTOS);
    }

    @Transactional
    public Lesson createLesson (Long courseId,LessonRequestDTO lessonRequestDTO){
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find course"));

        Lesson lesson = Lesson.builder()
                .week(lessonRequestDTO.getWeek())
                .name(lessonRequestDTO.getName())
                .content(lessonRequestDTO.getContent())
                .start(lessonRequestDTO.getStart())
                .course(course)
                .build();

        return lessonRepository.save(lesson);
    }

    @Transactional
    public Lesson updateLesson(Long lessonId, LessonRequestDTO lessonRequestDTO){
        Lesson existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find lesson"));

        existingLesson.updateLessonInfo(lessonRequestDTO.getWeek(),
                lessonRequestDTO.getName(),
                lessonRequestDTO.getContent(),
                lessonRequestDTO.getStart());

        return lessonRepository.save(existingLesson);
    }

    @Transactional
    public void deleteLesson(Long lessonId){
        Lesson lessonToDelete = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find lesson"));

        lessonRepository.delete(lessonToDelete);
    }

    public LessonResponseDTO createLessonDTO(Lesson lesson){
        return LessonResponseDTO.builder()
                .id(lesson.getId())
                .week(lesson.getWeek())
                .start(lesson.getStart())
                .name(lesson.getName())
                .content(lesson.getContent())
                .build();
    }

    public SyllabusDTO createSyllabusDTO(Course course, List<LessonResponseDTO> lessonResponseDTOS){
        return SyllabusDTO.builder()
                .summary(course.getSummary())
                .reference(course.getReference())
                .lessons(lessonResponseDTOS)
                .build();
    }
}
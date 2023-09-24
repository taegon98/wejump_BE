package wejump.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.lesson.LessonRequestDTO;
import wejump.server.api.dto.lesson.LessonResponseDTO;
import wejump.server.api.dto.syllabus.PlanDTO;
import wejump.server.api.dto.syllabus.SyllabusDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.course.CoursePlan;
import wejump.server.domain.lesson.Lesson;
import wejump.server.repository.CoursePlanRepository;
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
    private final CoursePlanRepository coursePlanRepository;

    // syllabus read (수정 완)
    @Transactional(readOnly = true)
    public SyllabusDTO getSyllabusById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find course"));

        List<PlanDTO> plans = coursePlanRepository.findByCourse_id(courseId).stream()
                .map(plan -> PlanDTO.builder()
                        .week(plan.getWeek())
                        .title(plan.getTitle())
                        .video(plan.getVideo())
                        .assignment(plan.getAssignment())
                        .build())
                .collect(Collectors.toList());

        return createSyllabusDTO(course, plans);
    }
    
    //delete and create syllabus 만들기
    @Transactional
    public List<CoursePlan> updateSyllabus (Long courseId, SyllabusDTO syllabusDTO){

        //update course info
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find course"));

        existingCourse.updateCourseInfo(syllabusDTO.getSummary(),
                syllabusDTO.getReference());

        courseRepository.save(existingCourse);

        //delete coursePlan
        List<CoursePlan> existingPlans = coursePlanRepository.findByCourse_id(courseId);
        coursePlanRepository.deleteAll(existingPlans);

        //create coursePlan
        List<CoursePlan> plans = syllabusDTO.getPlans().stream()
                .map(plan -> CoursePlan.builder()
                                .week(plan.getWeek())
                                .title(plan.getTitle())
                                .video(plan.getVideo() != null)
                                .assignment(plan.getAssignment() != null)
                                .course(existingCourse)
                                .build())
                .collect(Collectors.toList());

        return coursePlanRepository.saveAll(plans);
    }

    //이 아래는 일단 놔둘 것
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

    public SyllabusDTO createSyllabusDTO(Course course, List<PlanDTO> plans){
        return SyllabusDTO.builder()
                .summary(course.getSummary())
                .reference(course.getReference())
                .plans(plans)
                .build();
    }
}
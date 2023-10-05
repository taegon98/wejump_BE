package wejump.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.course.syllabus.PlanDTO;
import wejump.server.api.dto.course.syllabus.SyllabusDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.course.CoursePlan;
import wejump.server.repository.course.course.CoursePlanRepository;
import wejump.server.repository.course.course.CourseRepository;
import wejump.server.repository.course.lesson.LessonRepository;

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


//    //수강 신청
//    @PostMapping("/{courseId}/enroll/{memberId}")
//    public ResponseEntity<Object> enrollMemberToCourse(
//            @PathVariable Long courseId,
//            @PathVariable Long memberId
//    ) {
//        try {
//            // 코스 ID와 멤버 ID를 사용하여 멤버를 코스에 등록
//            courseService.enrollMemberToCourse(courseId, memberId);
//            return new ResponseEntity<>("Member enrolled successfully", HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }

    public SyllabusDTO createSyllabusDTO(Course course, List<PlanDTO> plans){
        return SyllabusDTO.builder()
                .summary(course.getSummary())
                .reference(course.getReference())
                .plans(plans)
                .build();
    }
}
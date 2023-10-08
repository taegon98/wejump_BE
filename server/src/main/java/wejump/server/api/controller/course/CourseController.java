package wejump.server.api.controller.course;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import wejump.server.api.dto.course.CourseRequestDTO;
import wejump.server.api.dto.course.CourseResponseDTO;
import wejump.server.api.dto.member.MemberResponseDTO;
import wejump.server.domain.course.Course;
import wejump.server.repository.LessonRepository;
import wejump.server.service.CourseService;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    private final LessonRepository lessonRepository;

    @PostMapping
    public ResponseEntity<Object> createCourse(@RequestBody @Valid CourseRequestDTO courseRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }


        Course createdCourse = courseService.createCourse(courseRequestDTO);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @GetMapping("/{courseId}")
    public CourseResponseDTO getCourseById(@PathVariable Long courseId) {
        //레슨 전체 찾기
        return courseService.getCourseById(courseId);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseRequestDTO courseRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 검증 오류가 있는 경우 처리
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        Course updatedCourse = courseService.updateCourse(courseId, courseRequestDTO);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable Long courseId) {
        try {
            courseService.deleteCourse(courseId);
            return new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //수강 신청
    @PostMapping("/{courseId}/enroll/{memberId}")
    public ResponseEntity<Object> enrollMemberToCourse(
            @PathVariable Long courseId,
            @PathVariable Long memberId
    ) {
        try {
            // 코스 ID와 멤버 ID를 사용하여 멤버를 코스에 등록
            courseService.enrollMemberToCourse(courseId, memberId);
            return new ResponseEntity<>("Member enrolled successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //과목 수강 인원 조회
    @GetMapping("{courseId}/members")
    public ResponseEntity<List<MemberResponseDTO>> getMembersEnrolledInCourse(@PathVariable Long courseId) {
        List<MemberResponseDTO> enrolledMembers = courseService.getMembersEnrolledInCourse(courseId)
                .stream()
                .map(MemberResponseDTO::new) // Member를 MemberResponseDTO로 매핑
                .collect(Collectors.toList());
        return ResponseEntity.ok(enrolledMembers);
    }

    @GetMapping
    public List<CourseResponseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }



}

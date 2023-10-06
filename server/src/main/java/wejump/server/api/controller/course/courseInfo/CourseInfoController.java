package wejump.server.api.controller.course.courseInfo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import wejump.server.api.dto.course.assignment.AssignmentRequestDTO;
import wejump.server.api.dto.course.assignment.AssignmentResponseDTO;
import wejump.server.api.dto.course.courseInfo.CourseInfoRequestDTO;
import wejump.server.api.dto.course.courseInfo.CourseInfoResponseDTO;
import wejump.server.api.dto.course.lesson.LessonRequestDTO;
import wejump.server.domain.assignment.Assignment;
import wejump.server.domain.assignment.Evaluate;
import wejump.server.domain.course.Course;
import wejump.server.domain.course.EnrollCourse;
import wejump.server.domain.lesson.Attend;
import wejump.server.domain.lesson.Lesson;
import wejump.server.service.course.assignment.AssignmentService;
import wejump.server.service.course.assignment.EvaluateService;
import wejump.server.service.course.course.CourseService;
import wejump.server.service.course.lesson.AttendService;
import wejump.server.service.course.lesson.LessonService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseInfoController {

    private final CourseService courseService;
    private final LessonService lessonService;
    private final AttendService attendService;
    private final AssignmentService assignmentService;
    private final EvaluateService evaluateService;

    // create Week
    @PostMapping("/material/{courseId}")
    public ResponseEntity<Object> createCourseInfo(@PathVariable Long courseId,
                                               @RequestBody @Valid CourseInfoRequestDTO courseInfoRequestDTO,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        // 해당 course 찾기
        Course course = courseService.getCourseById(courseId);

        // 해당 course를 수강하는 학생 찾기
        List<EnrollCourse> enrollCourses = course.getEnrolledCourses();
        if (enrollCourses.isEmpty()) {
            throw new IllegalArgumentException("No students enrolled in this course");
        }

        Lesson lesson = lessonService.createLesson(course, courseInfoRequestDTO.getLessonRequestDTO());
        List<Attend> attends = attendService.createAttend(lesson, enrollCourses);

        if (courseInfoRequestDTO.getAssignmentRequestDTO() != null) {
            Assignment assignment = assignmentService.createAssignment(lesson, courseInfoRequestDTO.getAssignmentRequestDTO());
            List<Evaluate> evaluates = evaluateService.createEvaluate(assignment, enrollCourses);
        }

        return new ResponseEntity<>("create success!", HttpStatus.CREATED);
    }


    // read All Week
    @GetMapping("/material/{courseId}")
    public List<CourseInfoResponseDTO> getCourseInfo(@PathVariable Long courseId){
        // 해당 course 찾기
        Course course = courseService.getCourseById(courseId);

        List<Lesson> lessons = course.getLessons();
        
        // lesson에 해당하는 assignment 찾아서 만들면 될듯
        List<CourseInfoResponseDTO> courseInfoResponseDTOS = lessons.stream()
                .map(lesson -> {
                    Assignment assignment = lesson.getAssignment();
                    AssignmentResponseDTO assignmentResponseDTO = null;
                    if (assignment != null){
                        assignmentResponseDTO = AssignmentResponseDTO.builder()
                                .title(assignment.getTitle())
                                .description(assignment.getDescription())
                                .end((assignment.getEnd()))
                                .build();
                    }
                    return CourseInfoResponseDTO.builder()
                            .week(lesson.getWeek())
                            .start(lesson.getStart())
                            .video(lesson.getVideo())
                            .assignmentResponseDTO(assignmentResponseDTO)
                            .build();
                })
                .collect(Collectors.toList());

        return courseInfoResponseDTOS;
    }


    // update lesson
    @PutMapping("/lesson/{lessonId}")
    public ResponseEntity<Object> updateLesson(@PathVariable Long lessonId,
                                               @RequestBody @Valid LessonRequestDTO lessonRequestDTO,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        // 해당 lesson 찾기
        Lesson lesson = lessonService.getLessonById(lessonId);
        Lesson updatedLesson = lessonService.updateLesson(lesson, lessonRequestDTO);
        return new ResponseEntity<>("update success!", HttpStatus.OK);
    }


    // delete lesson
    @DeleteMapping("/lesson/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable Long lessonId){
        // 해당 lesson 찾기
        Lesson lesson = lessonService.getLessonById(lessonId);

        lessonService.deleteLesson(lesson);
        return new ResponseEntity<>("delete success!", HttpStatus.OK);
    }

    // update Assignment
    @PutMapping("/assignment/{assignmentId}")
    public ResponseEntity<Object> updateAssignment(@PathVariable Long assignmentId,
                                                   @RequestBody @Valid AssignmentRequestDTO assignmentRequestDTO,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        // 해당 assignment 찾기
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        Assignment updatedAssignment = assignmentService.updateAssignment(assignment, assignmentRequestDTO);
        return new ResponseEntity<>("update success!", HttpStatus.OK);
    }


    // delete Assignment
    @DeleteMapping("/assignment/{assignmentId}")
    public ResponseEntity<Object> deleteAssignment(@PathVariable Long assignmentId){

        Assignment assignment = assignmentService.getAssignmentById(assignmentId);

        assignmentService.deleteAssignment(assignment);
        return new ResponseEntity<>("delete success!", HttpStatus.OK);
    }
}

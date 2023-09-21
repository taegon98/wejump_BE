package wejump.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import wejump.server.api.dto.lesson.LessonRequestDTO;
import wejump.server.api.dto.syllabus.SyllabusDTO;
import wejump.server.domain.course.CoursePlan;
import wejump.server.domain.lesson.Lesson;
import wejump.server.service.SyllabusService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{courseId}/syllabus")
@RequiredArgsConstructor
public class SyllabusController {
    private final SyllabusService syllabusService;

    // get syllabus 변경: course 정보도 담아서 주는걸로
    @GetMapping
    public SyllabusDTO getSyllabusById(@PathVariable Long courseId) {
        return syllabusService.getSyllabusById(courseId);
    }

    // update syllabus: course와 관련된 내용은 update, outline은 그냥 다 지우고 새로 생성
    @PutMapping("/update")
    public ResponseEntity<Object> updateSyllabus(@PathVariable Long courseId,
                                                 @RequestBody @Valid SyllabusDTO syllabusDTO,
                                                 BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        List<CoursePlan> updatedPlans = syllabusService.updateSyllabus(courseId, syllabusDTO);
        return new ResponseEntity<>(updatedPlans, HttpStatus.OK);
    }

    // create lesson 
    @PostMapping
    public ResponseEntity<Object> createLesson(@PathVariable Long courseId,
                                               @RequestBody @Valid LessonRequestDTO lessonRequestDTO,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        Lesson createdLesson = syllabusService.createLesson(courseId ,lessonRequestDTO);
        return new ResponseEntity<>(createdLesson, HttpStatus.CREATED);
    }

    // update lesson
    @PutMapping("/{lessonId}")
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

        Lesson updatedLesson = syllabusService.updateLesson(lessonId, lessonRequestDTO);
        return new ResponseEntity<>(updatedLesson, HttpStatus.OK);
    }


    // delete lesson
    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable Long lessonId){
        try{
            syllabusService.deleteLesson(lessonId);
            return new ResponseEntity<>("lesson deleted successfully", HttpStatus.OK);
        } catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

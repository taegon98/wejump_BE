package wejump.server.api.controller.course;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import wejump.server.api.dto.course.syllabus.PlanDTO;
import wejump.server.api.dto.course.syllabus.SyllabusDTO;
import wejump.server.domain.course.CoursePlan;
import wejump.server.service.SyllabusService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("course/{courseId}/syllabus")
@RequiredArgsConstructor
public class SyllabusController {
    private final SyllabusService syllabusService;

    // get syllabus 변경: course 정보도 담아서 주는걸로
    @GetMapping
    public SyllabusDTO getSyllabusById(@PathVariable Long courseId) {
        return syllabusService.getSyllabusById(courseId);
    }

    // update syllabus: course와 관련된 내용은 update, outline은 그냥 다 지우고 새로 생성
    @PutMapping
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

        for (PlanDTO plan : syllabusDTO.getPlans()) {
            if (plan.getWeek() == null || plan.getTitle() == null) {
                return new ResponseEntity<>("Week and Title cannot be null.", HttpStatus.BAD_REQUEST);
            }
        }

        List<CoursePlan> updatedPlans = syllabusService.updateSyllabus(courseId, syllabusDTO);
        return new ResponseEntity<>("update success!", HttpStatus.OK);
    }
}

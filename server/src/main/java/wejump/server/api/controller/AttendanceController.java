package wejump.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import wejump.server.api.dto.attendance.AttendanceRequestDTO;
import wejump.server.api.dto.attendance.AttendanceResponseDTO;
import wejump.server.api.dto.lesson.LessonRequestDTO;
import wejump.server.api.dto.lesson.LessonResponseDTO;
import wejump.server.domain.lesson.Attend;
import wejump.server.domain.lesson.Lesson;
import wejump.server.service.AttendanceService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{courseId}/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    // create attendance
    @PostMapping("/{userId}")
    public ResponseEntity<Object> createAttend(@PathVariable Long courseId,
                                               @PathVariable Long userId){
        List<Attend> createdAttend = attendanceService.createAttend(userId, courseId);
        return new ResponseEntity<>(createdAttend, HttpStatus.CREATED);
    }

    @GetMapping
    public List<AttendanceResponseDTO> getAttendanceById(@PathVariable Long courseId){
        return attendanceService.getAttendanceById(courseId);
    }

    @PutMapping
    public ResponseEntity<Object> updateAttend(@RequestBody @Valid List<AttendanceRequestDTO> attendanceRequestDTOS,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        List<AttendanceResponseDTO> updatedAttends = attendanceService.updateAttendance(attendanceRequestDTOS);
        return new ResponseEntity<>(updatedAttends, HttpStatus.OK);
    }

}
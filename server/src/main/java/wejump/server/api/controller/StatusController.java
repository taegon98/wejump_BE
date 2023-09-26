package wejump.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import wejump.server.api.dto.status.StatusRequestDTO;
import wejump.server.api.dto.status.StatusResponseDTO;
import wejump.server.api.dto.status.StatusDTO;
import wejump.server.domain.lesson.Status;
import wejump.server.service.StatusService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{courseId}/status")
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    // create attendance
    @PostMapping
    public ResponseEntity<Object> createStatus(@PathVariable Long courseId,
                                               @RequestBody @Valid StatusDTO statusDTO,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        // 날짜 형식: "2023-09-13"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate start = LocalDate.parse(statusDTO.getDate(), formatter);
            List<Status> createdStatus = statusService.createStatus(courseId, start, statusDTO.getAttendance(), statusDTO.getAssignment());
            return new ResponseEntity<>("create success", HttpStatus.CREATED);

        }
        catch (DateTimeParseException ex) {
            return new ResponseEntity<>("date format is not correct", HttpStatus.BAD_REQUEST);
        }
        catch (StatusService.NotFoundException notFoundException) {
            return new ResponseEntity<>("cannot find lesson", HttpStatus.BAD_REQUEST);
        }

    }

    // read all attendance and assignment
    @GetMapping
    public List<StatusResponseDTO> getStatusById(@PathVariable Long courseId){
        return statusService.getStatusById(courseId);
    }

    // update Status
    @PutMapping
    public ResponseEntity<Object> updateStatus(@RequestBody @Valid List<StatusRequestDTO> statusRequestDTOS,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        statusService.updateStatus(statusRequestDTOS);
        return new ResponseEntity<>("update success", HttpStatus.OK);
    }

    // delete Status
    @DeleteMapping
    public ResponseEntity<Object> deleteStatus(@PathVariable Long courseId,
                                               @RequestBody @Valid StatusDTO statusDTO,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate start = LocalDate.parse(statusDTO.getDate(), formatter);
            statusService.deleteStatus(courseId, start);
            return new ResponseEntity<>("delete success", HttpStatus.OK);

        }
        catch (DateTimeParseException ex) {
            return new ResponseEntity<>("date format is not correct", HttpStatus.BAD_REQUEST);
        }
    }

}

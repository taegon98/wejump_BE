package wejump.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wejump.server.api.dto.submit.SubmitRequestDTO;
import wejump.server.domain.assignment.Assignment;
import wejump.server.domain.assignment.Submit;
import wejump.server.service.AssignmentService;
import wejump.server.service.SubmitService;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/submits")
public class SubmitController {

    private final SubmitService submitService;
    private final AssignmentService assignmentService;

    @PostMapping("/{assignmentId}")
    public ResponseEntity<?> createSubmit(
            @PathVariable Long assignmentId,
            @RequestParam("file") MultipartFile file,
            @RequestBody @Valid SubmitRequestDTO submitDTO, BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        String comment = submitDTO.getComment();

        Submit submit = submitService.createSubmit(assignment, file, comment);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{submitId}")
    public ResponseEntity<?> deleteSubmit(@PathVariable Long submitId) {
        try {
            submitService.deleteSubmit(submitId);
            return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

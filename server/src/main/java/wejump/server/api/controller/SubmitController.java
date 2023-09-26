package wejump.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wejump.server.api.dto.assignment.AssignmentResponseDTO;
import wejump.server.api.dto.submit.SubmitRequestDTO;
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
            @RequestParam("comment") String comment
    ) throws IOException {

        AssignmentResponseDTO assignment = assignmentService.getAssignmentById(assignmentId);

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

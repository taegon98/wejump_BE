//package wejump.server.api.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import wejump.server.domain.assignment.Assignment;
//import wejump.server.domain.assignment.Submit;
//import wejump.server.domain.member.Member;
//import wejump.server.repository.member.MemberRepository;
//import wejump.server.service.assignment.AssignmentService;
//import wejump.server.service.assignment.SubmitService;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/submits")
//public class SubmitController {
//
//    private final SubmitService submitService;
//    private final AssignmentService assignmentService;
//
//    private final MemberRepository memberRepository;
//
//    @PostMapping("/{memberId}/{assignmentId}")
//    public ResponseEntity<?> createSubmit(
//            @PathVariable Long assignmentId,
//            @PathVariable Long memberId,
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("comment") String comment
//    ) throws IOException {
//
//        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
//        Member member = memberRepository.findById(memberId).get();
//
//        Submit submit = submitService.createSubmit(member, assignment, file, comment);
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{submitId}/download")
//    public ResponseEntity<Resource> downloadFile(@PathVariable Long submitId) throws MalformedURLException {
//        // 파일 경로를 얻기
//        Submit submit = submitService.getSubmitById(submitId);
//        String filePath = submit.getFilePath();
//
//        // Resource 객체 생성
//        Resource resource = submitService.loadFileAsResource(filePath);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }
//
//
//    @DeleteMapping("/{submitId}")
//    public ResponseEntity<?> deleteSubmit(@PathVariable Long submitId) {
//        try {
//            submitService.deleteSubmit(submitId);
//            return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//}

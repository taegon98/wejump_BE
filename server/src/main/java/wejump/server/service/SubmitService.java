package wejump.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wejump.server.domain.assignment.Assignment;
import wejump.server.domain.assignment.Submit;
import wejump.server.repository.SubmitRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubmitService {
    private final SubmitRepository submitRepository;

    @Value("${file.upload.path}")
    private String uploadPath;


    public Submit createSubmit(Assignment assignment, MultipartFile file, String comment) throws IOException {
        // 업로드된 파일을 서버에 저장
        String fileName = file.getOriginalFilename();
        String filePath = saveFile(file);

        Submit submit = Submit.builder()
                .assignment(assignment)
                .file(file)
                .filePath(filePath)
                .submissionTime(LocalDateTime.now())
                .comment(comment)
                .build();

        return submitRepository.save(submit);
    }

    private String saveFile(MultipartFile file) throws IOException {
        // 경로 생성
        String fileName = file.getOriginalFilename();
        String filePath = uploadPath + "/" + fileName;

        // 파일 저장
        Path destPath = Path.of(filePath);
        Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);

        return filePath;
    }

    public void deleteSubmit(Long submitId) {
        Submit submit = submitRepository.findById(submitId)
                .orElseThrow(() -> new IllegalArgumentException("제출을 찾을 수 없습니다."));
        submitRepository.delete(submit);
    }
}

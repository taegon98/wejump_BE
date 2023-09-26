package wejump.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.assignment.AssignmentRequestDTO;
import wejump.server.api.dto.assignment.AssignmentResponseDTO;
import wejump.server.domain.assignment.Assignment;
import wejump.server.repository.AssignmentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    @Transactional
    public Assignment createAssignment(AssignmentRequestDTO assignmentDTO) {
        // AssignmentDTO를 Assignment 엔티티로 변환
        Assignment assignment = convertToEntity(assignmentDTO);

        // Assignment 저장
        return assignmentRepository.save(assignment);
    }

    @Transactional
    public Assignment updateAssignment(Long assignmentId, AssignmentRequestDTO assignmentDTO) {
        // assignmentId로 기존 Assignment 조회
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("과제를 찾을 수 없습니다."));

        // AssignmentDTO를 Assignment 엔티티로 변환하여 기존 Assignment 업데이트
        Assignment assignment = convertToEntity(assignmentDTO);

        LocalDate startDate = assignmentDTO.getStartDate();
        LocalDate dueDate = assignmentDTO.getEndDate();

        assignment.updateAssignment(
                assignmentDTO.getTitle(),
                assignment.getDescription(),
                startDate,
                dueDate
        );

        // Assignment 업데이트
        return assignmentRepository.save(existingAssignment);
    }

    @Transactional
    public void deleteAssignment(Long assignmentId) {
        // assignmentId로 기존 Assignment를 조회
        Assignment assignmentToDelete = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("과제를 찾을 수 없습니다."));

        // Assignment 삭제
        assignmentRepository.delete(assignmentToDelete);
    }

    @Transactional(readOnly = true)
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Assignment getAssignmentById(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("과제를 찾을 수 없습니다."));
        return assignment;
    }

    private Assignment convertToEntity(AssignmentRequestDTO assignmentDTO) {
        LocalDate startDate = assignmentDTO.getStartDate();
        LocalDate dueDate = assignmentDTO.getEndDate();

        return Assignment.builder()
                .title(assignmentDTO.getTitle())
                .description(assignmentDTO.getDescription())
                .startDate(startDate)
                .dueDate(dueDate)
                .build();
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        // DateTimeFormatter를 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 파싱
        return LocalDateTime.parse(dateTimeString, formatter);
    }


}

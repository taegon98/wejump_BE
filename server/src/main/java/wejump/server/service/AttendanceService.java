package wejump.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import wejump.server.api.dto.status.StatusRequestDTO;
import wejump.server.api.dto.status.StatusResponseDTO;
import wejump.server.domain.course.EnrollCourse;
import wejump.server.domain.lesson.Attend;
import wejump.server.domain.lesson.Lesson;
import wejump.server.repository.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    private final LessonRepository lessonRepository;
    private final AttendRepository attendRepository;
    private final EnrollRepository enrollRepository;
    private final AssignmentRepository assignmentRepository;
    private final SubmitRepository submitRepository;

    @Transactional
    public List<Attend> createStatus (Long courseId, LocalDate start, Boolean attendance, Boolean assignment) {

        List<EnrollCourse> enrolls = enrollRepository.findAllByCourseId(courseId);

        Lesson lesson = lessonRepository.findByStart(start);

        if (lesson == null) {
            throw new NotFoundException();
        }

        if (attendance && assignment) {
            List<Attend> attends = enrolls.stream()
                    .map(enroll -> Attend.builder()
                            .member(enroll.getMember())
                            .lesson(lesson)
                            .attendance(false)
                            .assignment(false)
                            .build())
                    .collect(Collectors.toList());
            return attendRepository.saveAll(attends);
        } else if (attendance && !assignment) {
            List<Attend> attends = enrolls.stream()
                    .map(enroll -> Attend.builder()
                            .member(enroll.getMember())
                            .lesson(lesson)
                            .attendance(false)
                            .build())
                    .collect(Collectors.toList());
            return attendRepository.saveAll(attends);
        } else if (!attendance && assignment) {
            List<Attend> attends = enrolls.stream()
                    .map(enroll -> Attend.builder()
                            .member(enroll.getMember())
                            .lesson(lesson)
                            .assignment(false)
                            .build())
                    .collect(Collectors.toList());
            return attendRepository.saveAll(attends);
        }
        return null;
    }

    public List<StatusResponseDTO> getStatusById(Long courseId){

        List<Lesson> lessons = lessonRepository.findByCourse_Id(courseId);
        List<StatusResponseDTO> statusResponseDTOS = new ArrayList<>();

        for (Lesson lesson : lessons) {
            List<Attend> status = attendRepository.findByLessonId(lesson.getId());

            List<StatusResponseDTO> statusDTOs = status.stream()
                    .map(this::createStatusResponseDTO)
                    .collect(Collectors.toList());

            statusResponseDTOS.addAll(statusDTOs);
        }
        return statusResponseDTOS;
    }

    @Transactional
    public List<Attend> updateStatus(List<StatusRequestDTO> statusRequestDTOS){
        List<Attend> updatedStatus = statusRequestDTOS.stream()
                .map(this::updateStatusById)
                .collect(Collectors.toList());

        return updatedStatus;
    }

    private Attend updateStatusById(StatusRequestDTO statusRequestDTO){
        Attend existingStatus = attendRepository.findById(statusRequestDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("cannot find status"));

        existingStatus.updateStatusInfo(statusRequestDTO.getAttendance(), statusRequestDTO.getAssignment());
        return attendRepository.save(existingStatus);

    }

    private StatusResponseDTO createStatusResponseDTO(Attend status) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return StatusResponseDTO.builder()
                .id(status.getId())
                .name(status.getMember().getName())
                .date(status.getLesson().getStart().format(formatter))
                .attendance(status.getAttendance())
                .assignment(status.getAssignment())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends RuntimeException {
    }

}



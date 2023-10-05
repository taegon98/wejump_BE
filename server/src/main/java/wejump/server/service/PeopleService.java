package wejump.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import wejump.server.api.dto.course.people.StatusDTO;
import wejump.server.api.dto.course.people.StatusResponseDTO;
import wejump.server.domain.assignment.Assignment;
import wejump.server.domain.assignment.Evaluate;
import wejump.server.domain.lesson.Attend;
import wejump.server.domain.lesson.Lesson;
import wejump.server.repository.course.assignment.AssignmentRepository;
import wejump.server.repository.course.assignment.EvaluateRepository;
import wejump.server.repository.course.lesson.LessonRepository;
import wejump.server.repository.course.lesson.AttendRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PeopleService {

    private final LessonRepository lessonRepository;
    private final AttendRepository attendRepository;
    private final EvaluateRepository evaluateRepository;
    private final AssignmentRepository assignmentRepository;

//    @Transactional
//    public List<Attend> createAttend (Long courseId, Long lessonId) {
//
//    }

    @Transactional(readOnly = true)
    public List<StatusResponseDTO> getPeopleById(Long courseId){

        List<Lesson> lessons = lessonRepository.findAllByCourseIdOrderByStartAsc(courseId);

        List<StatusResponseDTO> statusResponseDTOS = new ArrayList<>();

        for (Lesson lesson : lessons) {
            List<Attend> attends = attendRepository.findAllByLessonIdOrderByName(lesson.getId());
            Assignment assignment = assignmentRepository.findByLessonId(lesson.getId());
            List<Evaluate> evaluates = evaluateRepository.findAllByAssignmentIdOrderByName(assignment.getId());

            List<Object> PeopleList = new ArrayList<>(attends);
            PeopleList.addAll(evaluates);

            Map<String, List<Object>> groupedByName = PeopleList.stream()
                    .collect(Collectors.groupingBy(item -> {
                        if (item instanceof Attend) {
                            return ((Attend) item).getName();
                        } else if (item instanceof Evaluate) {
                            return ((Evaluate) item).getName();
                        }
                        return null;
                    }));

            StatusResponseDTO status = StatusResponseDTO.builder()
                    .week(lesson.getWeek())
                    .statusByWeek(new ArrayList<>())
                    .build();

            for (Map.Entry<String, List<Object>> entry : groupedByName.entrySet()) {
                StatusDTO statusDTO = StatusDTO.builder()
                        .name(entry.getKey())
                        .statusByName(entry.getValue())
                        .build();
                status.setStatusByWeek(Collections.singletonList(statusDTO));
            }
            statusResponseDTOS.add(status);
        }
        return statusResponseDTOS;
    }

//
//    @Transactional
//    public void updateStatus(List<StatusRequestDTO> statusRequestDTOS){
//        List<Attend> updatedAttends = statusRequestDTOS.stream()
//                .map(this::updateStatusById)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public void deleteStatus(Long courseId, LocalDate date){
//        Lesson lesson = lessonRepository.findByCourseIdAndStart(courseId, date);
//        List<Attend> attendToDelete = attendRepository.findByLessonId(lesson.getId());
//
//        attendRepository.deleteAll(attendToDelete);
//    }
//
//
//    private Attend updateStatusById(StatusRequestDTO statusRequestDTO){
//        Attend existingAttend = attendRepository.findById(statusRequestDTO.getId())
//                .orElseThrow(() -> new IllegalArgumentException("cannot find status"));
//
//        existingAttend.updateStatusInfo(statusRequestDTO.getAttendance(), statusRequestDTO.getAssignment());
//        return attendRepository.save(existingAttend);
//
//    }
//
//    private StatusResponseDTO createStatusResponseDTO(Attend attend) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        return StatusResponseDTO.builder()
//                .id(attend.getId())
//                .name(attend.getMember().getName())
//                .date(attend.getLesson().getStart().format(formatter))
//                .attendance(attend.getAttendance())
//                .assignment(attend.getAssignment())
//                .build();
//    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends RuntimeException {
    }

}



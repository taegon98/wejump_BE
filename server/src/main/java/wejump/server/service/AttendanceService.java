package wejump.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.attendance.AttendanceRequestDTO;
import wejump.server.api.dto.attendance.AttendanceResponseDTO;
import wejump.server.api.dto.lesson.LessonRequestDTO;
import wejump.server.domain.lesson.Attend;
import wejump.server.domain.course.Course;
import wejump.server.domain.lesson.Lesson;
import wejump.server.domain.User;
import wejump.server.repository.AttendRepository;
import wejump.server.repository.CourseRepository;
import wejump.server.repository.LessonRepository;
import wejump.server.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final AttendRepository attendRepository;

    @Transactional
    public List<Attend> createAttend (Long userId, Long courseId){
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new IllegalArgumentException("cannot find user"));

        List<Lesson> lessons = lessonRepository.findByCourse_Id(courseId);

        List<Attend> attends = lessons.stream()
                .map(lesson -> Attend.builder()
                        .user(user)
                        .lesson(lesson)
                        .status("unknown")
                        .build())
                .collect(Collectors.toList());
        return attendRepository.saveAll(attends);
    }

    public List<AttendanceResponseDTO> getAttendanceById(Long courseId){

        List<Lesson> lessons = lessonRepository.findByCourse_Id(courseId);
        List<AttendanceResponseDTO> attendanceResponseDTOS = new ArrayList<>();

        for (Lesson lesson : lessons) {
            List<Attend> attends = attendRepository.findByLesson_Id(lesson.getId());

            List<AttendanceResponseDTO> attendDTOs = attends.stream()
                        .map(this::createAttendResponseDTO)
                        .collect(Collectors.toList());

            attendanceResponseDTOS.addAll(attendDTOs);
        }
        return attendanceResponseDTOS;
    }

    @Transactional
    public List<AttendanceResponseDTO> updateAttendance(List<AttendanceRequestDTO> attendanceRequestDTOS){
        List<Attend> updatedAttends = attendanceRequestDTOS.stream()
                .map(this::updateAttendById)
                .collect(Collectors.toList());

        List<AttendanceResponseDTO> attendanceResponseDTOS = updatedAttends.stream()
                .map(this::createAttendResponseDTO)
                .collect(Collectors.toList());

        return attendanceResponseDTOS;
    }

    private Attend updateAttendById(AttendanceRequestDTO attendanceRequestDTO){
        Attend existingAttend = attendRepository.findById(attendanceRequestDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("cannot find attned"));

        existingAttend.updateAttendInfo(attendanceRequestDTO.getStatus());
        return attendRepository.save(existingAttend);

    }

    private AttendanceResponseDTO createAttendResponseDTO(Attend attend) {
        return AttendanceResponseDTO.builder()
                .id(attend.getId())
                .name(attend.getUser().getName())
                .date(attend.getLesson().getStart())
                .status(attend.getStatus())
                .build();
    }
}

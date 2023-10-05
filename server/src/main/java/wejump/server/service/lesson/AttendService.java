package wejump.server.service.lesson;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.domain.course.EnrollCourse;
import wejump.server.domain.lesson.Attend;
import wejump.server.domain.lesson.AttendId;
import wejump.server.domain.lesson.Lesson;
import wejump.server.repository.lesson.AttendRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendService {
    private final AttendRepository attendRepository;

    @Transactional
    public List<Attend> createAttend(Lesson lesson, List<EnrollCourse> enrollCourses){

        List<Attend> attends = enrollCourses.stream()
                .map(enrollCourse -> {
                    AttendId attendId = new AttendId(lesson.getId(), enrollCourse.getMember().getId());
                    return Attend.builder()
                            .id(attendId)
                            .name(enrollCourse.getMember().getName())
                            .week(lesson.getWeek())
                            .lesson(lesson)
                            .member(enrollCourse.getMember())
                            .attendance("unknown")
                            .build();
                })
                .collect(Collectors.toList());

        return attendRepository.saveAll(attends);
    }

}

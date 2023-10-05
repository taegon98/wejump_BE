package wejump.server.service.lesson;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.course.lesson.LessonRequestDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.lesson.Lesson;
import wejump.server.repository.lesson.LessonRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {
    private final LessonRepository lessonRepository;

    @Transactional
    public Lesson getLessonById(Long lessonId){
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find lesson"));
        return lesson;
    }

    @Transactional
    public Lesson createLesson (Course course, LessonRequestDTO lessonRequestDTO){
        Lesson lesson = Lesson.builder()
                .week(lessonRequestDTO.getWeek())
                .video(lessonRequestDTO.getVideo())
                .start(lessonRequestDTO.getStart())
                .course(course)
                .build();

        return lessonRepository.save(lesson);
    }


    @Transactional
    public Lesson updateLesson(Lesson lesson, LessonRequestDTO lessonRequestDTO){

        lesson.updateLessonInfo(lessonRequestDTO.getWeek(),
                lessonRequestDTO.getVideo(),
                lessonRequestDTO.getStart());

        return lessonRepository.save(lesson);
    }

    @Transactional
    public void deleteLesson(Lesson lesson){

        lessonRepository.delete(lesson);
    }
}

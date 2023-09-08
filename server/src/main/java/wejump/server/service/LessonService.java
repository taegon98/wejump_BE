package wejump.server.service;
import wejump.server.domain.Lesson;
import wejump.server.repository.LessonRepository;
import java.util.List;

public class LessonService {
    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    //전체 수업 조회
    public List<Lesson> getAllLessons(){
        return lessonRepository.findAll();
    }
}

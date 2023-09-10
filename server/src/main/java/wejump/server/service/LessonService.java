package wejump.server.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wejump.server.domain.Lesson;
import wejump.server.repository.LessonRepository;
import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    //전체 수업 조회
    public List<Lesson> getLessonByCourseId(Long courseId){
        List<Lesson> lessonList = lessonRepository.findByCourse_Id(courseId);
        if (!lessonList.isEmpty()){
            return lessonList;
        }
        System.out.println("cannot find lesson");
        return null;
    }
}

package wejump.server.api.dto.lesson;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
public class LessonResponseDTO {
    private Long id;
    private Integer week;
    private LocalDate start;
    private String name;
    private String content;

//    public static List<Lesson> toLessons(Course course, List<LessonDTO> lessonDTOList){
//        List<Lesson> lessons = new ArrayList<>();
//
//        for (LessonDTO lessonDTO: lessonDTOList){
//            Lesson lesson = new Lesson();
//            lesson.setId(lessonDTO.getId());
//            lesson.setWeek(lessonDTO.getWeek());
//            lesson.setLesson_name(lessonDTO.getLessonName());
//            lesson.setContent(lessonDTO.getContent());
//            lesson.setLesson_date(lessonDTO.getLessonDate());
//            lesson.setCourse(course);
//            lessons.add(lesson);
//        }
//        return lessons;
//    }
}

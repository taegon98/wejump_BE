package wejump.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;
import wejump.server.domain.Course;
import wejump.server.domain.Lesson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private Long id;
    private int week;
    private LocalDate lessonDate;
    private String lessonName;
    private String content;

    public Long getId() {
        return id;
    }

    public int getWeek() {
        return week;
    }

    public LocalDate getLessonDate() {
        return lessonDate;
    }

    public String getLessonName() {
        return lessonName;
    }

    public String getContent() {
        return content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setLessonDate(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static List<Lesson> toLessons(Course course, List<LessonDTO> lessonDTOList){
        List<Lesson> lessons = new ArrayList<>();

        for (LessonDTO lessonDTO: lessonDTOList){
            Lesson lesson = new Lesson();
            lesson.setId(lessonDTO.getId());
            lesson.setWeek(lessonDTO.getWeek());
            lesson.setLesson_name(lessonDTO.getLessonName());
            lesson.setContent(lessonDTO.getContent());
            lesson.setLesson_date(lessonDTO.getLessonDate());
            lesson.setCourse(course);
            lessons.add(lesson);
        }
        return lessons;
    }
}

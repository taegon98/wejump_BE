package wejump.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private int week;
    private String lessonName;
    private String content;

    public int getWeek() {
        return week;
    }

    public String getLessonName() {
        return lessonName;
    }

    public String getContent() {
        return content;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

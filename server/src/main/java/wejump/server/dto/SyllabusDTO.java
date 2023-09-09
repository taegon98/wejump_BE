package wejump.server.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SyllabusDTO {
    private String summary;
    private String reference;
    private List<LessonDTO> lessons;

    public String getSummary() {
        return summary;
    }

    public String getReference() {
        return reference;
    }

    public List<LessonDTO> getLessons() {
        return lessons;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setLessons(List<LessonDTO> lessons) {
        this.lessons = lessons;
    }
}

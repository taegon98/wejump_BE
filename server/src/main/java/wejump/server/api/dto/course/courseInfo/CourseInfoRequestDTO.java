package wejump.server.api.dto.course.courseInfo;

import lombok.Getter;
import wejump.server.api.dto.course.assignment.AssignmentRequestDTO;
import wejump.server.api.dto.course.lesson.LessonRequestDTO;

@Getter
public class CourseInfoRequestDTO {

    private LessonRequestDTO lessonRequestDTO;

    private AssignmentRequestDTO assignmentRequestDTO;
}

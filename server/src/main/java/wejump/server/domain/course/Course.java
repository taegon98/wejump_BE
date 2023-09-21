package wejump.server.domain.course;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.domain.lesson.Lesson;
import wejump.server.api.dto.course.CourseResponseDTO;
import wejump.server.domain.member.Member;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DynamicUpdate
@Entity
@Getter
@Table(name = "course")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quota", nullable = false)
    private Integer quota;

    @Column(name = "start", nullable = false)
    private LocalDate start_date;

    @Column(name = "end", nullable = false)
    private LocalDate end_date;

    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Column(name = "summary", length = 100, nullable = false)
    private String summary;

    @Column(name = "reference", nullable = true)
    private String reference;

    @JsonBackReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @JsonBackReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CoursePlan> plans;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnrollCourse> enrolledCourses = new ArrayList<>();

    /*
     ****************************************비지니스 로직****************************************
     */

    // 비즈니스 로직을 통해 필드 값 변경
    
    public void updateCourseInfo(String name, Integer quota, LocalDate start_date, LocalDate end_date, String description, String summary, String reference) {

        this.name = name;
        this.quota = quota;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.summary = summary;
        this.reference = reference;
    }

//syllabus 에서 update
    public void updateCourseInfo(String summary, String reference){
        this.summary = summary;
        this.reference = reference;
    }

    public CourseResponseDTO build(Course course) {

        return CourseResponseDTO.builder()
                .name(course.getName())
                .quota(course.getQuota())
                .startDate(course.getStart_date())
                .endDate(course.getEnd_date())
                .description(course.getDescription())
                .summary(course.getSummary())
                .reference(course.getReference())
                .build();
    }

    /*
     ****************************************비지니스 로직****************************************
     */


    // 코스에 멤버를 등록하는 메서드
    public void addMember(Member member) {
        // 이미 등록된 멤버인지 체크
        if (!enrolledCourses.contains(member)) {
            LocalDateTime enrollDate = LocalDateTime.now();

            // yyyy-MM-dd 형식 포맷팅
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = enrollDate.format(formatter);

            EnrollCourse enrollCourse = EnrollCourse.builder()
                    .course(this)
                    .member(member)
                    .date(formattedDate)
                    .instructor(true)
                    .build();

            enrolledCourses.add(enrollCourse);

            // 멤버의 등록된 코스 목록에도 추가
            member.getEnrolledCourses().add(enrollCourse);
        }
    }


}

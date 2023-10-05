package wejump.server.domain.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.domain.lesson.Lesson;
import wejump.server.api.dto.course.course.CourseResponseDTO;
import wejump.server.domain.member.Member;
import wejump.server.domain.announcement.Announcement;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @Column(name = "start", nullable = true)
    private LocalDate start_date;

    @Column(name = "end", nullable = true)
    private LocalDate end_date;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "summary", nullable = true)
    private String summary;

    @Column(name = "reference", nullable = true)
    private String reference;

    @Column(name = "course_image", nullable = true)
    private String image;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CoursePlan> plans;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnrollCourse> enrolledCourses;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Announcement> announcements;

    /*
     ****************************************비지니스 로직****************************************
     */

    // 비즈니스 로직을 통해 필드 값 변경
    
    public void updateCourseInfo(String name, LocalDate start_date, LocalDate end_date, String description, String summary, String reference, String image) {

        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.summary = summary;
        this.reference = reference;
        this.image = image;
    }

//syllabus 에서 update
    public void updateCourseInfo(String summary, String reference){
        this.summary = summary;
        this.reference = reference;
    }

    public CourseResponseDTO build(Course course) {

        return CourseResponseDTO.builder()
                .name(course.getName())
                .startDate(course.getStart_date())
                .endDate(course.getEnd_date())
                .description(course.getDescription())
                .summary(course.getSummary())
                .reference(course.getReference())
                .image(course.getImage())
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

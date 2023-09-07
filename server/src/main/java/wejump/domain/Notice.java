package wejump.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnotice")
    private Long noticeId;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="announcement_date")
    private LocalDateTime announcementDate;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="course_idcourse")
    //private Course course;

    @Column(name="course_idcourse")
    private Long courseId;
}

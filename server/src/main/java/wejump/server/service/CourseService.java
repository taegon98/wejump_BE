package wejump.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.course.CourseRequestDTO;
import wejump.server.api.dto.course.CourseResponseDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.course.EnrollCourse;
import wejump.server.domain.member.Member;
import wejump.server.repository.CourseRepository;
import wejump.server.repository.EnrollRepository;
import wejump.server.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {
    private final CourseRepository courseRepository;

    private final MemberRepository memberRepository;

    private final EnrollRepository enrollRepository;

    @Transactional
    public Course createCourse(CourseRequestDTO courseRequestDTO) {

        Course course = Course.builder()
                .name("Course Name")
                .quota(50)
                .start_date("2023-09-10")
                .end_date("2023-12-31")
                .description("Course Description")
                .summary("Course Summary")
                .reference("Course Reference")
                .build();

        // 필수 필드 확인
        validateCourseFields(course);

        // 코스 저장
        return courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        // 엔티티를 DTO로 변환
        return courses.stream()
                .map(this::createCourseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseResponseDTO getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("코스를 찾을 수 없습니다."));
        // 엔티티를 DTO로 변환하여 반환
        return createCourseDTO(course);
    }

    // 엔티티를 DTO로 변환하는 메서드
    public CourseResponseDTO createCourseDTO(Course course) {
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

    @Transactional
    public Course updateCourse(Long courseId, CourseRequestDTO courseRequestDTO) {
        // courseId로 기존 코스를 조회
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("코스를 찾을 수 없습니다."));
        // 기존 코스 정보 업데이트
        existingCourse.updateCourseInfo(courseRequestDTO.getName(),
                courseRequestDTO.getQuota(),
                courseRequestDTO.getStart_date(),
                courseRequestDTO.getEnd_date(),
                courseRequestDTO.getDescription(),
                courseRequestDTO.getSummary(),
                courseRequestDTO.getReference());

        // 코스 저장 (업데이트)
        return courseRepository.save(existingCourse);
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        // courseId로 기존 코스를 조회
        Course courseToDelete = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("코스를 찾을 수 없습니다."));

        // 코스 삭제
        courseRepository.delete(courseToDelete);
    }

    private void validateCourseFields(Course course) {
        if (course.getName() == null || course.getName().isEmpty()) {
            throw new IllegalArgumentException("코스 이름은 필수입니다.");
        }
        if (course.getQuota() == null || course.getQuota() <= 0) {
            throw new IllegalArgumentException("수강 인원은 양수이어야 합니다.");
        }
        if (course.getStart_date() == null || course.getEnd_date() == null) {
            throw new IllegalArgumentException("시작 날짜와 종료 날짜는 필수입니다.");
        }
        if (course.getDescription() == null || course.getDescription().isEmpty()) {
            throw new IllegalArgumentException("코스 설명은 필수입니다.");
        }
        if (course.getSummary() == null || course.getSummary().isEmpty()) {
            throw new IllegalArgumentException("코스 요약은 필수입니다.");
        }
    }

    @Transactional
    public void enrollMemberToCourse(Long courseId, Long memberId) {
        // 코스 ID로 해당 코스 조회
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("코스를 찾을 수 없습니다."));

        // 멤버 ID로 해당 멤버 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));

        // 코스에 멤버를 등록
        course.addMember(member);
    }

    public List<Member> getMembersEnrolledInCourse(Long courseId) {
        List<EnrollCourse> enrollCourses = enrollRepository.findAllByCourseId(courseId);
        List<Member> enrolledMembers = enrollCourses.stream()
                .map(EnrollCourse::getMember)
                .collect(Collectors.toList());

        return enrolledMembers;
    }


}

package wejump.server.service;

import lombok.extern.slf4j.Slf4j;
import wejump.server.api.dto.notice.NoticeDto;
import wejump.server.api.dto.notice.NoticeResponseDto;
import wejump.server.domain.notice.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wejump.server.repository.NoticeRepository;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public List<NoticeResponseDto> index(Long courseId) {

        List<Notice> notices = noticeRepository.findAllByCourseId(courseId);
        List<NoticeResponseDto> responseDtos = notices.stream()
                .map(notice -> notice.toResponseDto())
                .collect(Collectors.toList());
        return responseDtos;
    }

    //없는 아이디 조회 시 빈 json, 응답코드 200
    public NoticeResponseDto show(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElse(null);
        return notice.toResponseDto();
    }

    public Notice create(NoticeDto dto, Long courseId) {
        Notice notice = dto.toNotice(courseId); //글 작성시간 이때 들어 감
        log.info(notice.toString());
        log.info("save");
        return noticeRepository.save(notice);
    }

    public Notice delete(Long id) {
        Notice target = noticeRepository.findById(id).orElse(null);
        if (target == null) {   //400대 에러 띄우기.
            return null;
        }
        noticeRepository.delete(target);
        return target;

    }

    public Notice update(Long id, NoticeDto dto) {
        Notice notice = dto.toNotice();
        log.info(notice.toString());
        Notice target = noticeRepository.findById(id).orElse(null);
        log.info(target.toString());
        if(target == null){
            log.info("fail");
            return null;
        }
        target.patch(notice);
        Notice updated = noticeRepository.save(target);
        return updated;
    }



}

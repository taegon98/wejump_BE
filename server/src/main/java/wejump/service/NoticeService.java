package wejump.service;

import lombok.extern.slf4j.Slf4j;
import wejump.api.dto.NoticeDto;
import wejump.domain.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wejump.repository.NoticeRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public List<Notice> index() {
        return noticeRepository.findAll();
    }

    public Notice show(Long noticeId) {
        return noticeRepository.findById(noticeId).orElse(null);
    }

    public Notice create(NoticeDto dto) {
        Notice notice = dto.toNotice();
        if(notice.getNoticeId() != null){
            log.info("already existing");
            return null;
        }
        log.info("save");
        return noticeRepository.save(notice);
    }

    public Notice delete(Long id) {
        Notice target = noticeRepository.findById(id).orElse(null);
        if (target == null) {
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

package wejump.service;

import wejump.api.dto.NoticeDto;
import wejump.domain.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wejump.repository.NoticeRepository;

import java.util.List;

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
            return null;
        }
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
}

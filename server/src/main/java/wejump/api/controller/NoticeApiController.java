package wejump.api.controller;

import wejump.api.dto.NoticeDto;
import wejump.domain.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wejump.service.NoticeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notice")
public class NoticeApiController {

    private final NoticeService noticeService;

    @GetMapping("")
    public List<Notice> index(){
        log.info("test");
        return noticeService.index();
    }

    @GetMapping("/{noticeId}")
    public Notice show(@PathVariable Long noticeId) {
        log.info("read");
        return noticeService.show(noticeId);
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<Notice> delete(@PathVariable Long noticeId) {
        Notice deleted = noticeService.delete(noticeId);

        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("")
    public ResponseEntity<Notice> create(@RequestBody NoticeDto dto) {
        log.info("create");
        Notice created = noticeService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                null;
                //ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}

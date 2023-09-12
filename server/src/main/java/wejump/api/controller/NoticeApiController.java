package wejump.api.controller;

import org.springframework.validation.annotation.Validated;
import wejump.api.dto.Notice.NoticeDto;
import wejump.api.dto.Notice.NoticeResponseDto;
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
@RequestMapping("/courses/{courseId}/notice") //코스별 notice, /course/{courseId}/notice
public class NoticeApiController {

    private final NoticeService noticeService;

    //모든 notice 조회
    @GetMapping("")
    public ResponseEntity<List<NoticeResponseDto>> index(@PathVariable Long courseId){
        log.info(courseId.toString());
        log.info("test");
        return new ResponseEntity<>(noticeService.index(courseId), HttpStatus.OK);
    }

    //특정 notice 조회
    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeResponseDto> show(@PathVariable Long noticeId) {
        log.info("read");
        return new ResponseEntity<>(noticeService.show(noticeId), HttpStatus.OK);
    }

    //특정 notice 삭제
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<NoticeResponseDto> delete(@PathVariable Long noticeId) {
        Notice deleted = noticeService.delete(noticeId);
        NoticeResponseDto responseDto = deleted.toResponseDto();

        return (responseDto != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //특정 notice 수정
    @PatchMapping("/{noticeId}")
    public ResponseEntity<NoticeResponseDto> update(@PathVariable Long noticeId, @RequestBody NoticeDto dto) {
        Notice updated = noticeService.update(noticeId, dto);
        NoticeResponseDto responseDto = updated.toResponseDto();
        return (responseDto != null)?
                ResponseEntity.status(HttpStatus.OK).body(responseDto):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //notice 등록
    @PostMapping("")
    public ResponseEntity<Notice> create(@RequestBody NoticeDto dto, @PathVariable Long courseId) {
        log.info("create");
        Notice created = noticeService.create(dto, courseId);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}

package wejump.server.api.controller.member;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wejump.server.api.dto.member.MemberResponseDTO;
import wejump.server.domain.member.Member;
import wejump.server.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class OAuthController {

    private final MemberRepository memberRepository;

    private static final String DEFAULT_ROLE = "Student";

    @GetMapping("/loginInfo")
    public ResponseEntity<MemberResponseDTO> oauthLoginInfo(Authentication authentication) {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            String email = attributes.get("email").toString();

            // 검색된 회원 정보 저장
            Member member;
            if (memberRepository.findByEmail(email).isPresent()) {
                member = memberRepository.findByEmail(email).get();
            } else {
                member = Member.builder()
                        .name(attributes.get("name").toString())
                        .email(email)
                        .role(DEFAULT_ROLE)
                        .build();
                memberRepository.save(member);
            }

            // 리다이렉션 응답 생성
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "http://localhost:5173/weJump_Fe/"); // 외부 URL로 절대 경로로 설정

            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER); // 303 See Other 리다이렉션 상태 코드

        } catch (Exception e) {
            log.error("OAuth login info error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

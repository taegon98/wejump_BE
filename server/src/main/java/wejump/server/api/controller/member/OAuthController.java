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

    private static final String FRONTEND_REDIRECT_URL = "http://localhost:5173/weJump_Fe/"; // 프론트엔드 리다이렉션 URI

    @GetMapping("/loginInfo")
    public ResponseEntity<MemberResponseDTO> oauthLoginInfo(Authentication authentication) {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            String email = attributes.get("email").toString();

            Member member;

            if (memberRepository.findByEmail(email).isPresent()) {
                member = memberRepository.findByEmail(email).get();
            } else {
                // 새로운 회원 생성
                member = Member.builder()
                        .name(attributes.get("name").toString())
                        .email(email)
                        .role(DEFAULT_ROLE)
                        .build();
                memberRepository.save(member);
            }

            // 멤버 정보를 MemberResponseDTO로 매핑
            MemberResponseDTO memberDTO = new MemberResponseDTO(
                    member.getId(), member.getName(), member.getEmail(), member.getRole());

            // 프론트엔드 리다이렉션 URI로 리디렉트
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", FRONTEND_REDIRECT_URL);
            return new ResponseEntity<>(memberDTO, headers, HttpStatus.SEE_OTHER); // 303 See Other 리다이렉션 상태 코드
        } catch (Exception e) {
            log.error("OAuth login info error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

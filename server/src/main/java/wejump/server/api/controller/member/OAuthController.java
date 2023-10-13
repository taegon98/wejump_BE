package wejump.server.api.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@CrossOrigin
public class OAuthController {

    private final MemberRepository memberRepository;
    @GetMapping("/loginInfo")
    public ResponseEntity<MemberResponseDTO> oauthLoginInfo(Authentication authentication){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = attributes.get("email").toString();

        Member member = new Member();

        if (memberRepository.findByEmail(email).isEmpty()) {
            Member new_member = Member.builder()
                    .name(attributes.get("name").toString())
                    .email(email)
                    .role("Student")
                    .build();
            memberRepository.save(new_member);
        }
        else member = memberRepository.findByEmail(email).get();

        MemberResponseDTO memberResponseDTO = new MemberResponseDTO(member.getId(), member.getName(), member.getEmail(), member.getRole());

        return new ResponseEntity<>(memberResponseDTO, HttpStatus.OK);
    }
}

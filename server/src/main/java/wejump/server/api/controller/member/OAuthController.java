package wejump.server.api.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wejump.server.domain.member.Member;
import wejump.server.repository.member.MemberRepository;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final MemberRepository memberRepository;
    @GetMapping("/loginInfo")
    public String oauthLoginInfo(Authentication authentication){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = attributes.get("email").toString();
        log.info(email);
        if (memberRepository.findByEmail(email).isEmpty()) {
            Member member = Member.builder()
                    .name(attributes.get("name").toString())
                    .email(email)
                    .build();
            memberRepository.save(member);
        }
        return attributes.toString();
    }
}

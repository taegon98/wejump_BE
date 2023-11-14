package wejump.server.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.CookieGenerator;
import wejump.server.api.dto.member.MemberResponseDTO;
import wejump.server.domain.member.Member;
import wejump.server.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
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
    public ResponseEntity<MemberResponseDTO> oauthLoginInfo(
            Authentication authentication,
            HttpServletResponse response
    ) {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            String email = attributes.get("email").toString();

            Member member;

            if (memberRepository.findByEmail(email).isPresent()) {
                member = memberRepository.findByEmail(email).get();
            } else {
                // Create a new member
                member = Member.builder()
                        .name(attributes.get("name").toString())
                        .email(email)
                        .role(DEFAULT_ROLE)
                        .build();
                memberRepository.save(member);
            }

            // Member information to MemberResponseDTO
            MemberResponseDTO memberDTO = new MemberResponseDTO(
                    member.getId(), member.getName(), member.getEmail(), member.getRole());

            // MemberResponseDTO to JSON and then encode as Base64
            ObjectMapper objectMapper = new ObjectMapper();
            String memberDTOJson = objectMapper.writeValueAsString(memberDTO);
            String base64EncodedMemberDTO = Base64.getEncoder().encodeToString(memberDTOJson.getBytes());

            // Store the Base64 encoded data in the cookie
            CookieGenerator cookieGenerator = new CookieGenerator();
            cookieGenerator.setCookieName("MEMBERDTO"); // Set your desired cookie name
            cookieGenerator.addCookie(response, base64EncodedMemberDTO);

            // Frontend redirection URI
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", FRONTEND_REDIRECT_URL);
            return new ResponseEntity<>(memberDTO, headers, HttpStatus.SEE_OTHER);
        } catch (Exception e) {
            log.error("OAuth login info error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

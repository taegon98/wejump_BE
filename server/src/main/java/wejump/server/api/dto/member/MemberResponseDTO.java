package wejump.server.api.dto.member;

import lombok.Getter;
import wejump.server.domain.member.Member;

@Getter
public class MemberResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String image;

    public MemberResponseDTO(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.image = member.getImage();
    }
}

package wejump.server.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor
@DynamicUpdate //update 할때 실제 값이 변경된 컬럼만 update 쿼리를 만듬
@Entity
@Getter
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = true, unique = true)
    private String nickname;

    @Column(name = "picture")
    private String picture;

    @Builder
    public Member(Long id, String name, String email, String nickname, String picture) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.picture = picture;
    }

    public Member update(String name, String email) {
        this.name = name;
        this.email = email;
        return this;
    }
}
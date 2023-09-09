package wejump.server.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(length = 30, nullable = false)
    private String user_name;

    @Getter
    @Column(length = 45, nullable = false)
    private String user_email;

    @Getter
    @Column(nullable = false)
    private String password;

    @Getter
    @Column(length = 45, nullable = false)
    private String profile_image;

    @Getter
    @Column(nullable = false)
    private String role = "student";

    @OneToMany(mappedBy = "user")
    private List<Attend> attends = new ArrayList<>();

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User() {
        this.role = "student"; // 기본값 설정
    }
}

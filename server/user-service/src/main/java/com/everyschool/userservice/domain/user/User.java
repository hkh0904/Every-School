package com.everyschool.userservice.domain.user;

import com.everyschool.userservice.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

/**
 * 회원 엔티티 클래스
 *
 * @author 임우택
 */
@Entity
@Getter
@Table(name = "`user`")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(length = 1)
public abstract class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false, updatable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 60)
    private String pwd;

    @Column(nullable = false, updatable = false, length = 20)
    private String name;

    @Column(nullable = false, updatable = false, length = 10)
    private String birth;

    @Column(unique = true, nullable = false, length = 36)
    private String userKey;

    @Column(nullable = false)
    private Integer userCodeId;

    protected User() {
        super();
    }

    protected User(String email, String pwd, String name, String birth, String userKey, int userCodeId) {
        this();
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.birth = birth;
        this.userKey = userKey;
        this.userCodeId = userCodeId;
    }

    //== 비즈니스 로직 ==//
    /**
     * 비밀번호 변경 로직
     *
     * @param newPwd 변경할 암호화 된 비밀번호
     */
    public void editPwd(String newPwd) {
        this.pwd = newPwd;
    }
}

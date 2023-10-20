package com.everyschool.userservice.domain.user;


import com.everyschool.userservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Table(name = "`user`")
public class User extends BaseEntity {

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
        this.userKey = UUID.randomUUID().toString();
    }

    @Builder
    private User(String email, String pwd, String name, String birth, int userCodeId) {
        this();
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.birth = birth;
        this.userCodeId = userCodeId;
    }
}

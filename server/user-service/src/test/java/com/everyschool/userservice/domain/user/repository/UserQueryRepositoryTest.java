package com.everyschool.userservice.domain.user.repository;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class UserQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("이미 등록된 이메일이 존재하면 true를 반환한다.")
    @Test
    void existEmail() {
        //given
        User user = saveUser();

        //when
        boolean isExistEmail = userQueryRepository.existEmail("ssafy@gmail.com");

        //then
        assertThat(isExistEmail).isTrue();
    }

    @DisplayName("이메일로 회원 정보를 조회한다.")
    @Test
    void findByEmail() {
        //given
        User user = saveUser();

        //when
        UserInfoResponse response = userQueryRepository.findByEmail("ssafy@gmail.com");

        //then
        assertThat(response.getEmail()).isEqualTo("ssafy@gmail.com");
    }

    private User saveUser() {
        User user = User.builder()
            .email("ssafy@gmail.com")
            .pwd(passwordEncoder.encode("ssafy1234@"))
            .name("김싸피")
            .birth("2001-01-01")
            .userKey(UUID.randomUUID().toString())
            .userCodeId(1)
            .build();
        return userRepository.save(user);
    }
}
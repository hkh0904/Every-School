package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class UserQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("입력 받은 이메일을 사용하는 회원 정보가 존재하지 않으면 예외가 발생한다.")
    @Test
    void searchUserWithoutEmail() {
        //given
        User user = saveUser();

        //when //then
        assertThatThrownBy(() -> userQueryService.searchUser("ssafy@naver.com"))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("이메일을 확인해주세요.");
    }

    @DisplayName("이메일을 입력 받아 회원 정보를 조회할 수 있다.")
    @Test
    void searchUser() {
        //given
        User user = saveUser();

        //when
        UserInfoResponse response = userQueryService.searchUser("ssafy@gmail.com");

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
package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.api.controller.user.response.UserClientResponse;
import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.ParentRepository;
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
    private ParentRepository parentRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("입력 받은 이메일을 사용하는 회원 정보가 존재하지 않으면 예외가 발생한다.")
    @Test
    void searchUserWithoutEmail() {
        //given
        User user = saveUser();

        //when //then
        assertThatThrownBy(() -> userQueryService.searchUser(UUID.randomUUID().toString()))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("이메일을 입력 받아 회원 정보를 조회할 수 있다.")
    @Test
    void searchUser() {
        //given
        User user = saveUser();

        //when
        UserInfoResponse response = userQueryService.searchUser(user.getUserKey());

        //then
        assertThat(response.getEmail()).isEqualTo("ssafy@gmail.com");
    }

    @DisplayName("입력 받은 이름과 생년월일이 일치하는 회원 정보가 존재하지 않으면 예외가 발생한다.")
    @Test
    void searchEmailWithoutNameAndBirth() {
        //given

        //when //then
        assertThatThrownBy(() -> userQueryService.searchEmail("김싸피", "2001-01-01"))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("일치하는 회원 정보가 존재하지 않습니다.");
    }

    @DisplayName("탈퇴한 회원의 이메일을 찾는 경우 예외가 발생한다.")
    @Test
    void searchEmailWithdrawal() {
        //given
        User user = saveUser();
        user.remove();

        //when //then
        assertThatThrownBy(() -> userQueryService.searchEmail("김싸피", "2001-01-01"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 탈퇴한 회원입니다.");
    }

    @DisplayName("회원의 이름과 생년월일을 입력 받아 이메일을 조회할 수 있다.")
    @Test
    void searchEmail() {
        //given
        User user = saveUser();

        //when
        String maskingEmail = userQueryService.searchEmail("김싸피", "2001-01-01");

        //then
        assertThat(maskingEmail).isEqualTo("ssa**@gmail.com");
    }

    @DisplayName("회원 고유키가 일치하는 회원이 존재하지 않으면 예외가 발생한다.")
    @Test
    void searchUserIdWithoutUser() {
        //given

        //when //then
        assertThatThrownBy(() -> userQueryService.searchUserId(UUID.randomUUID().toString()))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("일치하는 회원 정보가 존재하지 않습니다.");
    }

    @DisplayName("회원 고유키를 입력 받아 회원 PK를 조회할 수 있다.")
    @Test
    void searchUserId() {
        //given
        User user = saveUser();

        //when
        UserClientResponse response = userQueryService.searchUserId(user.getUserKey());

        //then
        assertThat(response.getUserId()).isEqualTo(user.getId());
    }

    private User saveUser() {
        Parent parent = Parent.builder()
            .email("ssafy@gmail.com")
            .pwd(passwordEncoder.encode("ssafy1234@"))
            .name("김싸피")
            .birth("2001-01-01")
            .userKey(UUID.randomUUID().toString())
            .userCodeId(1)
            .parentType("M")
            .build();
        return parentRepository.save(parent);
    }
}
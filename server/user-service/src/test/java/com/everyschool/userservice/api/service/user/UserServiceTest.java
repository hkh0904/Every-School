package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.controller.user.response.WithdrawalResponse;
import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.ParentRepository;
import com.everyschool.userservice.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("비밀번호 변경 시 입력 받은 이메일을 사용하는 회원이 존재하지 않으면 예외가 발생한다.")
    @Test
    void editPwdWithoutUser() {
        //given

        //when //then
        assertThatThrownBy(() -> userService.editPwd(UUID.randomUUID().toString(), "ssafy1234@", "ssafy1111@"))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("등록이 되지 않은 회원입니다.");
    }

    @DisplayName("비밀번호 변경 시 입력 받은 현재 비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void editPwdNotMatchCurrentPwd() {
        //given
        User user = saveUser();

        //when //then
        assertThatThrownBy(() -> userService.editPwd(user.getUserKey(), "ssafy5678@", "ssafy1111@"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("현재 비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("이메일, 현재 비밀번호, 새로운 비밀번호를 입력 받아 계정 비밀번호를 수정한다.")
    @Test
    void editPwd() {
        //given
        User user = saveUser();

        //when
        UserResponse response = userService.editPwd(user.getUserKey(), "ssafy1234@", "ssafy1111@");

        //then
        assertThat(response.getEmail()).isEqualTo("ssafy@gmail.com");
    }

    @DisplayName("회원 탈퇴 시 입력 받은 이메일을 사용하는 회원이 존재하지 않으면 예외가 발생한다.")
    @Test
    void withdrawalWithoutUser() {
        //given

        //when //then
        assertThatThrownBy(() -> userService.withdrawal(UUID.randomUUID().toString(), "ssafy1234@"))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("등록이 되지 않은 회원입니다.");
    }

    @DisplayName("회원 탈퇴 시 입력 받은 비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void withdrawalNotMatchPwd() {
        //given
        User user = saveUser();

        //when //then
        assertThatThrownBy(() -> userService.withdrawal(user.getUserKey(), "ssafy5678@"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("현재 비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("이메일과 비밀번호를 입력 받아 회원 탈퇴를 한다.")
    @Test
    void withdrawal() {
        //given
        User user = saveUser();

        //when
        WithdrawalResponse response = userService.withdrawal(user.getUserKey(), "ssafy1234@");

        //then
        Optional<User> findUser = userRepository.findById(user.getId());
        assertThat(findUser).isPresent();
        assertThat(findUser.get().isDeleted()).isTrue();
    }

    @DisplayName("비밀번호 초기화 시 입력 받은 이메일을 사용하는 회원 정보가 존재하지 않으면 예외가 발생한다.")
    @Test
    void editRandomPwdWithoutEmail() {
        //given

        //when //then
        assertThatThrownBy(() -> userService.editRandomPwd("ssafy@gmail.com", "김싸피", "2001-01-01"))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("이메일을 확인해주세요.");
    }

    @DisplayName("비밀번호 초기화 시 입력 받은 이름과 생년월일이 계정 정보와 불일치한다면 예외가 발생한다.")
    @Test
    void editRandomPwdNotMatchNameAndBirth() {
        //given
        User user = saveUser();

        //when //then
        assertThatThrownBy(() -> userService.editRandomPwd("ssafy@gmail.com", "김싸피", "2001-01-10"))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("일치하는 회원 정보가 존재하지 않습니다.");
    }

    @DisplayName("비밀번호 초기화 시 탈퇴한 회원이라면 예외가 발생한다.")
    @Test
    void editRandomPwdWithdrawal() {
        //given
        User user = saveUser();
        user.remove();

        //when //then
        assertThatThrownBy(() -> userService.editRandomPwd("ssafy@gmail.com", "김싸피", "2001-01-01"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 탈퇴한 회원입니다.");
    }

    @DisplayName("이메일, 이름, 생년월일을 입력 받아 비밀번호를 랜덤한 10자로 수정한다.")
    @Test
    void editRandomPwd() {
        //given
        User user = saveUser();

        //when
        String randomPwd = userService.editRandomPwd("ssafy@gmail.com", "김싸피", "2001-01-01");

        //then
        Optional<User> findUser = userRepository.findById(user.getId());
        assertThat(findUser).isPresent();

        boolean isMatch = passwordEncoder.matches(randomPwd, findUser.get().getPwd());
        assertThat(isMatch).isTrue();
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
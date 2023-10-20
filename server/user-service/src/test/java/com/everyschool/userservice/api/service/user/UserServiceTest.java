package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.service.user.dto.CreateUserDto;
import com.everyschool.userservice.api.service.user.exception.DuplicateException;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("이미 사용 중인 이메일이라면 예외가 발생한다.")
    @Test
    void createUserDuplicateEmail() {
        //given
        User user = saveUser();

        CreateUserDto dto = CreateUserDto.builder()
            .userCodeId(1)
            .email("ssafy@gmail.com")
            .pwd("ssafy5678@")
            .name("이싸피")
            .birth("2002-02-02")
            .build();

        //when //then
        assertThatThrownBy(() -> userService.createUser(dto))
            .isInstanceOf(DuplicateException.class)
            .hasMessage("이미 사용 중인 이메일 입니다.");
    }

    @DisplayName("회원 정보를 입력 받아 회원을 생성한다.")
    @Test
    void createUser() {
        //given
        CreateUserDto dto = CreateUserDto.builder()
            .userCodeId(1)
            .email("ssafy@gmail.com")
            .pwd("ssafy1234@")
            .name("김싸피")
            .birth("2001-01-01")
            .build();

        //when
        UserResponse response = userService.createUser(dto);

        //then
        assertThat(response.getEmail()).isEqualTo("ssafy@gmail.com");
    }

    private User saveUser() {
        User user = User.builder()
            .email("ssafy@gmail.com")
            .pwd(passwordEncoder.encode("ssafy1234@"))
            .name("김싸피")
            .birth("2001-01-01")
            .userCodeId(1)
            .build();
        return userRepository.save(user);
    }
}
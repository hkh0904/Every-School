package com.everyschool.userservice.domain.user.repository;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("이메일로 회원 엔티티를 조회한다.")
    @Test
    void findByEmail() {
        //given
        User user = saveUser();

        //when
        Optional<User> findUser = userRepository.findByEmail("ssafy@gmail.com");

        //then
        assertThat(findUser).isPresent();
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
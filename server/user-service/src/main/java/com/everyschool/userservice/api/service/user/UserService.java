package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.controller.user.response.WithdrawalResponse;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse editPwd(String email, String currentPwd, String newPwd) {
        User user = getUserEntity(email);

        equalPwd(currentPwd, user.getPwd());

        String encodedPwd = passwordEncoder.encode(newPwd);
        user.editPwd(encodedPwd);

        return UserResponse.of(user);
    }

    public WithdrawalResponse withdrawal(String email, String pwd) {
        User user = getUserEntity(email);

        equalPwd(pwd, user.getPwd());

        user.remove();

        return WithdrawalResponse.of(user);
    }

    public String editRandomPwd(String email, String name, String birth) {
        User user = getUserEntity(email);

        checkUserInfo(name, birth, user);

        checkWithdrawal(user);

        return editRandomPwd(user);
    }

    private User getUserEntity(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException("이메일을 확인해주세요.");
        }
        return findUser.get();
    }

    private void equalPwd(String targetPwd, String encodedPwd) {
        boolean isMatch = passwordEncoder.matches(targetPwd, encodedPwd);
        if (!isMatch) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
    }

    private void checkUserInfo(String name, String birth, User user) {
        if (!user.getName().equals(name) || !user.getBirth().equals(birth)) {
            throw new NoSuchElementException("일치하는 회원 정보가 존재하지 않습니다.");
        }
    }

    private void checkWithdrawal(User user) {
        if (user.isDeleted()) {
            throw new IllegalArgumentException("이미 탈퇴한 회원입니다.");
        }
    }

    public String createRandomPwd() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) (random.nextInt(26) + 97)); break;
                case 1: key.append((char) (random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    private String editRandomPwd(User user) {
        String randomPwd = createRandomPwd();
        String encodedPwd = passwordEncoder.encode(randomPwd);
        user.editPwd(encodedPwd);
        return randomPwd;
    }
}

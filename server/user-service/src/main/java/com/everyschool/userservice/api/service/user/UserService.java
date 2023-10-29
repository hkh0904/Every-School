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

    /**
     * 비밀번호 변경
     *
     * @param email 변경할 계정의 이메일
     * @param currentPwd 현재 비밀번호
     * @param newPwd 변경할 비밀번호
     * @return 변경된 회원 정보
     */
    public UserResponse editPwd(String userKey, String currentPwd, String newPwd) {
        Optional<User> findUser = userRepository.findByUserKey(userKey);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }
        User user = findUser.get();

        equalPwd(currentPwd, user.getPwd());

        String encodedPwd = passwordEncoder.encode(newPwd);
        user.editPwd(encodedPwd);

        return UserResponse.of(user);
    }

    /**
     * 회원 탈퇴
     *
     * @param email 탈퇴할 계정의 이메일
     * @param pwd 계정 비밀번호
     * @return 탈퇴한 회원 정보
     */
    public WithdrawalResponse withdrawal(String userKey, String pwd) {
        Optional<User> findUser = userRepository.findByUserKey(userKey);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }
        User user = findUser.get();

        equalPwd(pwd, user.getPwd());

        user.remove();

        return WithdrawalResponse.of(user);
    }

    /**
     * 10자리 랜덤한 비밀번호로 변경
     *
     * @param email 변경할 계정의 이메일
     * @param name 변경할 계정의 사용자 이름
     * @param birth 변경할 계정의 사용자 생년월일
     * @return 변경된 회원 정보
     */
    public String editRandomPwd(String email, String name, String birth) {
        User user = getUserEntity(email);

        checkUserInfo(name, birth, user);

        checkWithdrawal(user);

        return editRandomPwd(user);
    }

    /**
     * 이메일로 회원 엔티티 조회
     *
     * @param email 조회할 이메일
     * @return 조회된 회원 엔티티
     * @throws NoSuchElementException 이메일로 조회된 회원이 존재하지 않을 때
     */
    private User getUserEntity(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException("이메일을 확인해주세요.");
        }
        return findUser.get();
    }

    /**
     * 비밀번호 일치 여부 확인
     *
     * @param targetPwd 확인할 비밀번호
     * @param encodedPwd 인코딩된 비밀번호
     * @throws IllegalArgumentException 비밀번호가 일치하지 않을 때
     */
    private void equalPwd(String targetPwd, String encodedPwd) {
        boolean isMatch = passwordEncoder.matches(targetPwd, encodedPwd);
        if (!isMatch) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 회원의 이름과 생년월일 일치 여부 확인
     *
     * @param user 대상 회원 엔티티
     * @param name 회원 이름
     * @param birth 회원 생년월일
     * @throws NoSuchElementException 회원 정보가 하나라도 일치하지 않을 때
     */
    private void checkUserInfo(String name, String birth, User user) {
        if (!user.getName().equals(name) || !user.getBirth().equals(birth)) {
            throw new NoSuchElementException("일치하는 회원 정보가 존재하지 않습니다.");
        }
    }

    /**
     * 회원 탈퇴 여부 확인
     *
     * @param user 확인할 회원 엔티티
     * @throws IllegalArgumentException 탈퇴한 회원인 경우 발생
     */
    private void checkWithdrawal(User user) {
        if (user.isDeleted()) {
            throw new IllegalArgumentException("이미 탈퇴한 회원입니다.");
        }
    }

    /**
     * 영문과 숫자로 이루어진 10자리 랜덤한 문자 생성
     *
     * @return 10자리 랜덤한 비밀번호
     */
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

    /**
     * 랜덤한 비밀번호로 변경
     *
     * @param user 변경할 회원 엔티티
     * @return 랜덤한 비밀번호
     */
    private String editRandomPwd(User user) {
        String randomPwd = createRandomPwd();
        String encodedPwd = passwordEncoder.encode(randomPwd);
        user.editPwd(encodedPwd);
        return randomPwd;
    }
}

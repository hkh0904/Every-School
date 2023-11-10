package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.Student;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.StudentParentRepository;
import com.everyschool.userservice.domain.user.repository.UserRepository;
import com.everyschool.userservice.messagequeue.dto.ParentSchoolApplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.userservice.message.ErrorMessage.*;

@RequiredArgsConstructor
@Service
@Transactional
public class StudentParentService {

    private final StudentParentRepository studentParentRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public ParentSchoolApplyDto createStudentParent(String parentKey, String connectCode) {
        User user = getUserByUserKey(parentKey);

        Parent parent = convertToParent(user);

        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String studentKey = operations.get(connectCode);

        user = getUserByUserKey(studentKey);
        Student student = convertToStudent(user);

        return ParentSchoolApplyDto.of(student, parent);
    }

    /**
     * 회원 고유키로 회원 엔티티 조회
     *
     * @param userKey 회원 고유키
     * @return 조회된 회원 엔티티
     * @throws IllegalArgumentException 등록된 회원이 아닌 경우 발생
     */
    private User getUserByUserKey(String userKey) {
        Optional<User> findUser = userRepository.findByUserKey(userKey);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_USER.getMessage());
        }
        return findUser.get();
    }

    /**
     * 회원 엔티티를 학부모 엔티티로 변환
     *
     * @param user 회원 엔티티
     * @return 변환된 학부모 엔티티
     * @throws IllegalArgumentException 학부모 회원이 아닌 경우 발생
     */
    private Parent convertToParent(User user) {
        if (!(user instanceof Parent)) {
            throw new IllegalArgumentException(NOT_PARENT_USER.getMessage());
        }
        return (Parent) user;
    }

    /**
     * 회원 엔티티를 학생 엔티티로 변환
     *
     * @param user 회원 엔티티
     * @return 변환된 학생 엔티티
     * @throws IllegalArgumentException 학생 회원이 아닌 경우 발생
     */
    private Student convertToStudent(User user) {
        if (!(user instanceof Student)) {
            throw new IllegalArgumentException(NOT_STUDENT_USER.getMessage());
        }
        return (Student) user;
    }
}

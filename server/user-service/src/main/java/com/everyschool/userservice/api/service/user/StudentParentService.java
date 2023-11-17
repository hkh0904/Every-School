package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.Student;
import com.everyschool.userservice.domain.user.StudentParent;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.StudentParentRepository;
import com.everyschool.userservice.domain.user.repository.UserRepository;
import com.everyschool.userservice.messagequeue.dto.ParentSchoolApplyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.userservice.message.ErrorMessage.*;

/**
 * 보호자 명령 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class StudentParentService {

    private final StudentParentRepository studentParentRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 학생 연결 코드 확인
     *
     * @param parentKey   학부모 고유키
     * @param connectCode 연결 코드
     * @return 카프카 큐잉 메세지
     */
    public ParentSchoolApplyDto checkStudentParent(String parentKey, String connectCode) {
        User user = getUserByUserKey(parentKey);

        Parent parent = convertToParent(user);

        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String studentKey = operations.get(connectCode);

        user = getUserByUserKey(studentKey);
        Student student = convertToStudent(user);

        return ParentSchoolApplyDto.of(student, parent);
    }

    /**
     * 자녀 관계 연결
     *
     * @param studentId 학생(자녀) 아이디
     * @param parentId  학부모 아이디
     */
    public void createStudentParent(Long studentId, Long parentId) {
        log.debug("[Service] 부모 자녀 연결");
        User user = getUserById(parentId);
        Parent parent = convertToParent(user);

        user = getUserById(studentId);
        Student student = convertToStudent(user);

        StudentParent studentParent = StudentParent.builder()
            .parent(parent)
            .student(student)
            .build();

        StudentParent savedStudentParent = studentParentRepository.save(studentParent);
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
     * 회원 아이디로 회원 엔티티 조회
     *
     * @param userId 회원 아이디
     * @return 조회된 회원 엔티티
     * @throws IllegalArgumentException 등록된 회원이 아닌 경우 발생
     */
    private User getUserById(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
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

package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.api.controller.user.response.CreateStudentParentResponse;
import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.Student;
import com.everyschool.userservice.domain.user.StudentParent;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.StudentParentRepository;
import com.everyschool.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class StudentParentService {

    private final StudentParentRepository studentParentRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public CreateStudentParentResponse createStudentParent(String parentUserKey, String connectCode) {
        Optional<User> findParent = userRepository.findByUserKey(parentUserKey);
        if (findParent.isEmpty()) {
            throw new NoSuchElementException();
        }

        if (!(findParent.get() instanceof Parent)) {
            throw new IllegalArgumentException();
        }

        Parent parent = (Parent) findParent.get();

        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String studentUseKey = operations.get(connectCode);

        Optional<User> findStudent = userRepository.findByUserKey(studentUseKey);
        if (findStudent.isEmpty()) {
            throw new NoSuchElementException();
        }

        if (!(findStudent.get() instanceof Student)) {
            throw new IllegalArgumentException();
        }

        Student student = (Student) findStudent.get();

        StudentParent studentParent = StudentParent.builder()
            .student(student)
            .parent(parent)
            .build();

        StudentParent savedStudentParent = studentParentRepository.save(studentParent);

        return CreateStudentParentResponse.of(savedStudentParent);
    }
}

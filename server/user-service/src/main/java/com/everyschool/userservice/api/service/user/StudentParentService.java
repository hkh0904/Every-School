package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.api.controller.user.response.CreateStudentParentResponse;
import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.Student;
import com.everyschool.userservice.domain.user.StudentParent;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.StudentParentRepository;
import com.everyschool.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public CreateStudentParentResponse createStudentParent(String studentEmail, String parentEmail) {
        Student student = (Student) getUserEntity(studentEmail);

        Parent parent = (Parent) getUserEntity(parentEmail);

        StudentParent studentParent = StudentParent.builder()
            .student(student)
            .parent(parent)
            .build();

        StudentParent savedStudentParent = studentParentRepository.save(studentParent);

        return CreateStudentParentResponse.of(savedStudentParent);
    }

    private User getUserEntity(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException();
        }
        return findUser.get();
    }
}

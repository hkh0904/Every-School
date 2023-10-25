package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.service.user.dto.CreateUserDto;
import com.everyschool.userservice.api.service.user.exception.DuplicateException;
import com.everyschool.userservice.domain.user.Student;
import com.everyschool.userservice.domain.user.repository.StudentRepository;
import com.everyschool.userservice.domain.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserQueryRepository userQueryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse createStudent(CreateUserDto dto) {
        emailDuplicateValidation(dto.getEmail());

        String userKey = UUID.randomUUID().toString();
        String encodedPwd = passwordEncoder.encode(dto.getPwd());

        Student savedStudent = insertStudent(dto, encodedPwd, userKey);

        return UserResponse.of(savedStudent);
    }
    private void emailDuplicateValidation(String email) {
        boolean isExistEmail = userQueryRepository.existEmail(email);
        if (isExistEmail) {
            throw new DuplicateException("이미 사용 중인 이메일 입니다.");
        }
    }

    private Student insertStudent(CreateUserDto dto, String encodedPwd, String userKey) {
        Student student = Student.builder()
            .email(dto.getEmail())
            .pwd(encodedPwd)
            .name(dto.getName())
            .birth(dto.getBirth())
            .userKey(userKey)
            .userCodeId(dto.getUserCodeId())
            .build();
        return studentRepository.save(student);
    }
}

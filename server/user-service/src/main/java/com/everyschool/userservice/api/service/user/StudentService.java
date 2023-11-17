package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.service.user.dto.CreateUserDto;
import com.everyschool.userservice.api.service.user.exception.DuplicateException;
import com.everyschool.userservice.domain.user.Student;
import com.everyschool.userservice.domain.user.repository.StudentRepository;
import com.everyschool.userservice.domain.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.everyschool.userservice.message.ErrorMessage.NO_SUCH_USER;

@RequiredArgsConstructor
@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserQueryRepository userQueryRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    public UserResponse createStudent(CreateUserDto dto) {
        emailDuplicateValidation(dto.getEmail());

        String userKey = UUID.randomUUID().toString();
        String encodedPwd = passwordEncoder.encode(dto.getPwd());

        Student savedStudent = insertStudent(dto, encodedPwd, userKey);

        return UserResponse.of(savedStudent);
    }

    public String generateConnectCode(String userKey) {
        String code = createCode();

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        operations.set(code, userKey, 3, TimeUnit.MINUTES);

        return code;
    }

    public void editClassInfo(Long studentId, Long schoolId, Long schoolClassId) {
        Optional<Student> findStudent = studentRepository.findById(studentId);
        if (findStudent.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_USER.getMessage());
        }
        Student student = findStudent.get();

        Student editedStudent = student.editClassInfo(schoolId, schoolClassId);
    }

    public String createCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) (random.nextInt(26) + 97)); break;
                case 1: key.append((char) (random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
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

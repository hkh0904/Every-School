package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.service.user.dto.CreateUserDto;
import com.everyschool.userservice.api.service.user.exception.DuplicateException;
import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.repository.ParentRepository;
import com.everyschool.userservice.domain.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class ParentService {

    private final ParentRepository parentRepository;
    private final UserQueryRepository userQueryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse createParent(CreateUserDto dto, String parentType) {
        emailDuplicateValidation(dto.getEmail());

        String userKey = UUID.randomUUID().toString();
        String encodedPwd = passwordEncoder.encode(dto.getPwd());

        Parent savedParent = insertParent(dto, parentType, encodedPwd, userKey);

        return UserResponse.of(savedParent);
    }

    private void emailDuplicateValidation(String email) {
        boolean isExistEmail = userQueryRepository.existEmail(email);
        if (isExistEmail) {
            throw new DuplicateException("이미 사용 중인 이메일 입니다.");
        }
    }

    private Parent insertParent(CreateUserDto dto, String parentType, String encodedPwd, String userKey) {
        Parent parent = Parent.builder()
            .email(dto.getEmail())
            .pwd(encodedPwd)
            .name(dto.getName())
            .birth(dto.getBirth())
            .userKey(userKey)
            .userCodeId(dto.getUserCodeId())
            .parentType(parentType)
            .build();
        return parentRepository.save(parent);
    }
}

package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.domain.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserQueryRepository userQueryRepository;

    public UserInfoResponse searchUser(String email) {
        Optional<UserInfoResponse> response = userQueryRepository.findByEmail(email);
        if (response.isEmpty()) {
            throw new NoSuchElementException("이메일을 확인해주세요.");
        }
        return response.get();
    }

    public String searchEmail(String name, String birth) {
        return null;
    }
}

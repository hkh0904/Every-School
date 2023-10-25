package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.domain.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserQueryRepository userQueryRepository;

    public UserInfoResponse searchUser(String email) {
        return null;
    }
}

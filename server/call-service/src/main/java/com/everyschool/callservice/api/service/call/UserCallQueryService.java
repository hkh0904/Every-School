package com.everyschool.callservice.api.service.call;

import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.everyschool.callservice.domain.call.repository.UserCallQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserCallQueryService {

    private final UserCallQueryRepository userCallQueryRepository;
    private final UserServiceClient userServiceClient;

    /**
     * 내 통화 목록 조회
     *
     * @param userKey 회원 키
     * @return 조회된 통화 목록 리스트
     */
    public List<UserCallResponse> searchMyCalls(String userKey) {
        log.debug("call UserCallQueryService#searchMyCalls");
        UserInfo user = getUser(userKey);

        if (user.getUserType() == 'T') {
            return userCallQueryRepository.findAllByTeacherId(user.getUserId());
        }

        return userCallQueryRepository.findAllById(user.getUserId());
    }

    public UserCallResponse searchMyCall(Long callId) {
        log.debug("call UserCallQueryService#searchMyCalls");

        return userCallQueryRepository.findById(callId);
    }

    private UserInfo getUser(String userKey) {
        UserInfo user = userServiceClient.searchUserInfoByUserKey(userKey);
        log.debug("get user from user-serivce = {}", user);

        return user;
    }
}

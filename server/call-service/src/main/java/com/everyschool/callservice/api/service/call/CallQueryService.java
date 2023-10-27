package com.everyschool.callservice.api.service.call;

import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.controller.call.response.CallResponse;
import com.everyschool.callservice.domain.call.repository.CallQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class CallQueryService {

    private final CallQueryRepository callQueryRepository;
    private final UserServiceClient userServiceClient;

    /**
     * 내 통화 목록 조회
     *
     * @param userKey 회원 키
     * @return 조회된 통화 목록 리스트
     */
    public List<CallResponse> searchMyCalls(String userKey) {
        UserInfo user = userServiceClient.searchUserInfoByUserKey(userKey);
        log.debug("call CallQueryService#searchMyCalls");
        log.debug("get user from user-serivce = {}", user);

        if (user.getUserType() == 'T') {
            return callQueryRepository.findAllByTeacherId(user.getUserId());
        }

        return callQueryRepository.findAllById(user.getUserId());
    }
}

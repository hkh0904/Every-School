package com.everyschool.callservice.api.service.usercall;

import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.controller.usercall.response.UserCallReportResponse;
import com.everyschool.callservice.api.controller.usercall.response.UserCallDetailsResponse;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.everyschool.callservice.domain.usercall.repository.UserCallQueryRepository;
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

    /**
     * 내 통화 상세 조회
     *
     * @param userKey 통화 id
     * @return 조회된 통화 상세 목록 리스트
     */
    public UserCallReportResponse searchMyCallDetails(Long userCallId) {
        log.debug("call UserCallQueryService#searchMyCallRecords");

        UserCallReportResponse response = userCallQueryRepository.findByCallId(userCallId);
        List<UserCallDetailsResponse> details = userCallQueryRepository.findAllByCallId(userCallId);
        response.setDetails(details);
        return response;
    }

    private UserInfo getUser(String userKey) {
        UserInfo user = userServiceClient.searchUserInfoByUserKey(userKey);
        log.debug("get user from user-serivce = {}", user);

        return user;
    }
}

package com.everyschool.schoolservice.api.app.service.schoolapply;

import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.domain.schoolapply.repository.SchoolApplyAppQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SchoolApplyAppQueryService {

    private final SchoolApplyAppQueryRepository schoolApplyAppQueryRepository;
    private final UserServiceClient userServiceClient;

    public boolean existApply(String userKey) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        boolean result = schoolApplyAppQueryRepository.isExistApply(userInfo.getUserId());

        return result;
    }

}

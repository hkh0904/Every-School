package com.everyschool.consultservice.api.app.service.consult;

import com.everyschool.consultservice.api.app.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.domain.consult.repository.ConsultAppQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConsultAppQueryService {

    private final ConsultAppQueryRepository consultAppQueryRepository;
    private final UserServiceClient userServiceClient;

    public List<ConsultResponse> searchConsults(String userKey, int schoolYear) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        List<ConsultResponse> consults = consultAppQueryRepository.findByParentIdAndSchoolYear(userInfo.getUserId(), schoolYear);

        return consults;
    }
}

package com.everyschool.consultservice.api.web.service.consult;

import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.api.web.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.domain.consult.repository.ConsultWebQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ConsultWebQueryService {

    private final ConsultWebQueryRepository consultWebQueryRepository;
    private final UserServiceClient userServiceClient;

    public List<ConsultResponse> searchConsults(String userKey, int schoolYear, Long schoolId, int status) {
        UserInfo teacherInfo = userServiceClient.searchUserInfo(userKey);

        List<ConsultResponse> responses = consultWebQueryRepository.findByTeacherId(schoolYear, schoolId, teacherInfo.getUserId(), status);

        return responses;
    }
}

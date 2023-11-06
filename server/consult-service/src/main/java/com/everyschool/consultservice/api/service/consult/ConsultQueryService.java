package com.everyschool.consultservice.api.service.consult;

import com.everyschool.consultservice.api.client.SchoolServiceClient;
import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.ConsultUserInfo;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.api.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.controller.consult.response.WebConsultResponse;
import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.repository.ConsultQueryRepository;
import com.everyschool.consultservice.domain.consult.repository.ConsultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.everyschool.consultservice.error.ErrorMessage.UNREGISTERED_CONSULT;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConsultQueryService {

    private final ConsultRepository consultRepository;
    private final ConsultQueryRepository consultQueryRepository;
    private final UserServiceClient userServiceClient;
    private final SchoolServiceClient schoolServiceClient;

    public List<WebConsultResponse> searchConsults(String userKey, int schoolYear, int status) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        List<Consult> findConsults = consultQueryRepository.findByTeacherIdAndSchoolYear(userInfo.getUserId(), schoolYear, status);

        List<Long> temp = new ArrayList<>();
        for (Consult findConsult : findConsults) {
            temp.add(findConsult.getStudentId());
            temp.add(findConsult.getParentId());
        }

        List<ConsultUserInfo> consultUserInfos = schoolServiceClient.searchConsultUser(temp);

        Map<Long, String> map = consultUserInfos.stream()
            .collect(Collectors.toMap(ConsultUserInfo::getUserId, ConsultUserInfo::getUserInfo, (a, b) -> b));

        return findConsults.stream()
            .map(consult -> WebConsultResponse.of(consult, map.get(consult.getStudentId()), map.get(consult.getParentId())))
            .collect(Collectors.toList());
    }

    public ConsultDetailResponse searchConsult(Long consultId) {
        Optional<Consult> findConsult = consultRepository.findById(consultId);
        if (findConsult.isEmpty()) {
            throw new NoSuchElementException(UNREGISTERED_CONSULT.getMessage());
        }
        Consult consult = findConsult.get();

        return ConsultDetailResponse.of(consult);
    }
}

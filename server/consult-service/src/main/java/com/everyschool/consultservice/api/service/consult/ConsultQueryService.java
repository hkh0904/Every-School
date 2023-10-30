package com.everyschool.consultservice.api.service.consult;

import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.api.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.repository.ConsultQueryRepository;
import com.everyschool.consultservice.domain.consult.repository.ConsultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConsultQueryService {

    private final ConsultRepository consultRepository;
    private final ConsultQueryRepository consultQueryRepository;
    private final UserServiceClient userServiceClient;

    public List<ConsultResponse> searchConsults(String userKey) {
        UserInfo userInfo = userServiceClient.searchByUserKey(userKey);

        List<Consult> findConsults = consultQueryRepository.findByParentId(userInfo.getUserId(), userInfo.getUserType());

        return findConsults.stream()
            .map(consult -> ConsultResponse.of(consult, userInfo.getUserType()))
            .collect(Collectors.toList());
    }

    public ConsultDetailResponse searchConsult(Long consultId) {
        Optional<Consult> findConsult = consultRepository.findById(consultId);
        if (findConsult.isEmpty()) {
            throw new NoSuchElementException();
        }
        Consult consult = findConsult.get();

        return ConsultDetailResponse.of(consult);
    }
}

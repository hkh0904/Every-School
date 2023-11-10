package com.everyschool.consultservice.api.app.service.consult;

import com.everyschool.consultservice.api.app.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.app.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.domain.consult.repository.ConsultAppQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.consultservice.error.ErrorMessage.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConsultAppQueryService {

    private final ConsultAppQueryRepository consultAppQueryRepository;
    private final UserServiceClient userServiceClient;

    public List<ConsultResponse> searchConsultsByParent(String userKey, int schoolYear) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        List<ConsultResponse> consults = consultAppQueryRepository.findByParentIdAndSchoolYear(userInfo.getUserId(), schoolYear);

        return consults;
    }

    public List<ConsultResponse> searchConsultsByTeacher(String userKey, Integer schoolYear) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        List<ConsultResponse> consults = consultAppQueryRepository.findByTeacherIdAndSchoolYear(userInfo.getUserId(), schoolYear);

        return consults;
    }

    public ConsultDetailResponse searchConsult(Long consultId) {
        Optional<ConsultDetailResponse> findResponse = consultAppQueryRepository.findById(consultId);
        if (findResponse.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_CONSULT.getMessage());
        }
        return findResponse.get();
    }
}

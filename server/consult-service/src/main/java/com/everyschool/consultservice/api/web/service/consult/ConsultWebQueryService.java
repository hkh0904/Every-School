package com.everyschool.consultservice.api.web.service.consult;

import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.api.web.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.web.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.domain.consult.repository.ConsultWebQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.consultservice.error.ErrorMessage.*;

/**
 * 상담 웹 조회 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConsultWebQueryService {

    private final ConsultWebQueryRepository consultWebQueryRepository;
    private final UserServiceClient userServiceClient;

    /**
     * 상담 목록 조회
     *
     * @param userKey    회원 고유키
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param status     상담 진행 상태 코드
     * @return 조회된 상담 목록 리스트
     */
    public List<ConsultResponse> searchConsults(String userKey, int schoolYear, Long schoolId, int status) {
        UserInfo teacherInfo = userServiceClient.searchUserInfo(userKey);

        List<ConsultResponse> responses = consultWebQueryRepository.findByTeacherId(schoolYear, schoolId, teacherInfo.getUserId(), status);

        return responses;
    }

    /**
     * 상담 상세 조회
     *
     * @param consultId 상담 아이디
     * @return 조회된 상담 상세 내용
     */
    public ConsultDetailResponse searchConsult(Long consultId) {
        Optional<ConsultDetailResponse> response = consultWebQueryRepository.findById(consultId);

        if (response.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_CONSULT.getMessage());
        }

        return response.get();
    }
}

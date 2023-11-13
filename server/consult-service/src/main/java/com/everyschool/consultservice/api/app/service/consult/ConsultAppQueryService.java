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

/**
 * 앱 상담 조회용 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConsultAppQueryService {

    private final ConsultAppQueryRepository consultAppQueryRepository;
    private final UserServiceClient userServiceClient;

    /**
     * 학부모용 상담 내역 목록 조회
     *
     * @param userKey    회원 고유키
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 상담 내역 목록
     */
    public List<ConsultResponse> searchConsultsByParent(String userKey, int schoolYear, Long schoolId) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        List<ConsultResponse> consults = consultAppQueryRepository.findByParentId(userInfo.getUserId(), schoolYear, schoolId);

        return consults;
    }

    /**
     * 교직원용 상담 내역 목록 조회
     *
     * @param userKey    회원 고유키
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 상담 내역 목록
     */
    public List<ConsultResponse> searchConsultsByTeacher(String userKey, Integer schoolYear, Long schoolId) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        List<ConsultResponse> consults = consultAppQueryRepository.findByTeacherId(userInfo.getUserId(), schoolYear, schoolId);

        return consults;
    }

    /**
     * 상담 상세 내역 조회
     *
     * @param consultId 상담 아이디
     * @return 조회된 상담 상세 내역
     */
    public ConsultDetailResponse searchConsult(Long consultId) {
        Optional<ConsultDetailResponse> findResponse = consultAppQueryRepository.findById(consultId);
        if (findResponse.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_CONSULT.getMessage());
        }
        return findResponse.get();
    }
}

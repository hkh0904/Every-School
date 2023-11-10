package com.everyschool.consultservice.api.web.service.consult;

import com.everyschool.consultservice.api.controller.consult.response.ApproveConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.FinishConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.RejectConsultResponse;
import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.repository.ConsultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.consultservice.error.ErrorMessage.NO_SUCH_CONSULT;

/**
 * 상담 웹 명령 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional
public class ConsultWebService {

    private final ConsultRepository consultRepository;

    /**
     * 상담 승인
     *
     * @param consultId 상담 아이디
     * @return 상담 승인 결과
     */
    public ApproveConsultResponse approveConsult(Long consultId) {
        Consult consult = getConsultEntity(consultId);

        Consult editedConsult = consult.approval();

        return ApproveConsultResponse.of(editedConsult);
    }

    /**
     * 상담 완료
     *
     * @param consultId     상담 아이디
     * @param resultContent 상담 결과 내용
     * @return 상담 완료 결과
     */
    public FinishConsultResponse finishConsult(Long consultId, String resultContent) {
        Consult consult = getConsultEntity(consultId);

        Consult editedConsult = consult.finish(resultContent);

        return FinishConsultResponse.of(editedConsult);
    }

    /**
     * 상담 거절
     *
     * @param consultId      상담 아이디
     * @param rejectedReason 상담 거절 사유
     * @return 상담 거절 결과
     */
    public RejectConsultResponse rejectConsult(Long consultId, String rejectedReason) {
        Consult consult = getConsultEntity(consultId);

        Consult editedConsult = consult.reject(rejectedReason);

        return RejectConsultResponse.of(editedConsult);
    }

    /**
     * 상담 엔티티 조회
     *
     * @param consultId 상담 아이디
     * @return 조회된 상담 엔티티
     */
    private Consult getConsultEntity(Long consultId) {
        Optional<Consult> findConsult = consultRepository.findById(consultId);
        if (findConsult.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_CONSULT.getMessage());
        }
        return findConsult.get();
    }
}

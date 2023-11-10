package com.everyschool.consultservice.api.web.controller.consult;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.controller.consult.request.FinishConsultRequest;
import com.everyschool.consultservice.api.controller.consult.request.RejectConsultRequest;
import com.everyschool.consultservice.api.controller.consult.response.ApproveConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.FinishConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.RejectConsultResponse;
import com.everyschool.consultservice.api.service.consult.ConsultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 상담 웹 명령 API 컨트롤러
 *
 * @author 임우택
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/consult-service/v1/web/{schoolYear}/schools/{schoolId}/consults")
public class ConsultWebController {

    private final ConsultService consultService;

    /**
     * 상담 신청 승인 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param consultId  상담 아이디
     * @return 상담 신청 승인 결과
     */
    @PatchMapping("/{consultId}/approve")
    public ApiResponse<ApproveConsultResponse> approveConsult(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long consultId
    ) {
        log.debug("call ConsultWebController#approveConsult");

        ApproveConsultResponse response = consultService.approveConsult(consultId);
        log.debug("ApproveConsultResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 상담 완료 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param consultId  상담 아이디
     * @param request    상담 결과
     * @return 상담 완료 결과
     */
    @PatchMapping("/{consultId}/finish")
    public ApiResponse<FinishConsultResponse> finishConsult(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long consultId,
        @Valid @RequestBody FinishConsultRequest request
    ) {
        log.debug("call ConsultWebController#finishConsult");

        FinishConsultResponse response = consultService.finishConsult(consultId, request.getResultContent());
        log.debug("FinishConsultResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 상담 거절 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param consultId  상담 아이디
     * @param request    거절 사유
     * @return 상담 거절 결과
     */
    @PatchMapping("/{consultId}/reject")
    public ApiResponse<RejectConsultResponse> rejectConsult(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long consultId,
        @Valid @RequestBody RejectConsultRequest request
    ) {
        log.debug("call ConsultWebController#rejectConsult");

        RejectConsultResponse response = consultService.rejectConsult(consultId, request.getRejectedReason());
        log.debug("ApproveConsultResponse={}", response);

        return ApiResponse.ok(response);
    }
}
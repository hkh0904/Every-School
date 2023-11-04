package com.everyschool.consultservice.api.controller.consult;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.controller.consult.request.FinishConsultRequest;
import com.everyschool.consultservice.api.controller.consult.request.RejectConsultRequest;
import com.everyschool.consultservice.api.controller.consult.response.ApproveConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.FinishConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.RejectConsultResponse;
import com.everyschool.consultservice.api.service.consult.ConsultService;
import com.everyschool.consultservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/consult-service/v1/web/{schoolYear}/schools/{schoolId}/consults")
public class ConsultWebController {

    private final ConsultService consultService;
    private final TokenUtils tokenUtils;

    @PatchMapping("/{consultId}/approve")
    public ApiResponse<ApproveConsultResponse> approveConsult(
        @PathVariable Long schoolId,
        @PathVariable Integer schoolYear,
        @PathVariable Long consultId
    ) {
        log.debug("call ConsultWebController#approveConsult");

        ApproveConsultResponse response = consultService.approveConsult(consultId);
        log.debug("ApproveConsultResponse={}", response);

        return ApiResponse.ok(response);
    }

    @PatchMapping("/{consultId}/finish")
    public ApiResponse<FinishConsultResponse> finishConsult(
        @PathVariable Long schoolId,
        @PathVariable Integer schoolYear,
        @PathVariable Long consultId,
        @Valid @RequestBody FinishConsultRequest request
    ) {
        log.debug("call ConsultWebController#finishConsult");

        FinishConsultResponse response = consultService.finishConsult(consultId, request.getResultContent());
        log.debug("FinishConsultResponse={}", response);

        return ApiResponse.ok(response);
    }

    @PatchMapping("/{consultId}/reject")
    public ApiResponse<RejectConsultResponse> rejectConsult(
        @PathVariable Long schoolId,
        @PathVariable Integer schoolYear,
        @PathVariable Long consultId,
        @Valid @RequestBody RejectConsultRequest request
    ) {
        log.debug("call ConsultWebController#rejectConsult");

        RejectConsultResponse response = consultService.rejectConsult(consultId, request.getRejectedReason());
        log.debug("ApproveConsultResponse={}", response);

        return ApiResponse.ok(response);
    }
}
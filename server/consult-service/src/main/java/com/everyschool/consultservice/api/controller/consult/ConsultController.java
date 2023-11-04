package com.everyschool.consultservice.api.controller.consult;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.controller.consult.request.CreateConsultRequest;
import com.everyschool.consultservice.api.controller.consult.request.RejectConsultRequest;
import com.everyschool.consultservice.api.controller.consult.response.ApproveConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.CreateConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.RejectConsultResponse;
import com.everyschool.consultservice.api.service.consult.ConsultService;
import com.everyschool.consultservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/consult-service/v1/schools/{schoolId}/consults/{schoolYear}")
public class ConsultController {

    private final ConsultService consultService;
    private final TokenUtils tokenUtils;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateConsultResponse> createConsult(@Valid @RequestBody CreateConsultRequest request, @PathVariable Long schoolId) {
        log.debug("call ConsultController#createConsult");
        log.debug("CreateConsultRequest={}", request);

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        CreateConsultResponse response = consultService.createConsult(userKey, schoolId, request.toDto());
        log.debug("result={}", response);

        return ApiResponse.created(response);
    }

    @PatchMapping("/{consultId}/approve")
    public ApiResponse<ApproveConsultResponse> approveConsult(
        @PathVariable Long schoolId,
        @PathVariable Integer schoolYear,
        @PathVariable Long consultId
    ) {
        log.debug("call ConsultController#approveConsult");

        ApproveConsultResponse response = consultService.approveConsult(consultId);
        log.debug("ApproveConsultResponse={}", response);

        return ApiResponse.ok(response);
    }

    @PatchMapping("/{consultId}/reject")
    public ApiResponse<RejectConsultResponse> rejectConsult(
        @PathVariable Long schoolId,
        @PathVariable Integer schoolYear,
        @PathVariable Long consultId,
        @Valid @RequestBody RejectConsultRequest request
    ) {
        log.debug("call ConsultController#approveConsult");

        RejectConsultResponse response = consultService.rejectConsult(consultId, request.getRejectedReason());
        log.debug("ApproveConsultResponse={}", response);

        return ApiResponse.ok(response);
    }
}
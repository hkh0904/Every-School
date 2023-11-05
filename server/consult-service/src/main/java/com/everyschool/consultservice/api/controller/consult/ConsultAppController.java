package com.everyschool.consultservice.api.controller.consult;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.controller.consult.request.CreateConsultRequest;
import com.everyschool.consultservice.api.controller.consult.response.CreateConsultResponse;
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
@RequestMapping("/consult-service/v1/app/{schoolYear}/schools/{schoolId}/consults")
public class ConsultAppController {

    private final ConsultService consultService;
    private final TokenUtils tokenUtils;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateConsultResponse> createConsult(@Valid @RequestBody CreateConsultRequest request, @PathVariable Long schoolId) {
        log.debug("call ConsultAppController#createConsult");
        log.debug("CreateConsultRequest={}", request);

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        CreateConsultResponse response = consultService.createConsult(userKey, schoolId, request.toDto());
        log.debug("result={}", response);

        return ApiResponse.created(response);
    }
}
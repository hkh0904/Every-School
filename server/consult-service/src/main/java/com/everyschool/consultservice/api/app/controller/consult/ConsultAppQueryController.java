package com.everyschool.consultservice.api.app.controller.consult;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.app.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.api.app.service.consult.ConsultAppQueryService;
import com.everyschool.consultservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/consult-service/v1/app/{schoolYear}/schools/{schoolId}/consults")
public class ConsultAppQueryController {

    private final ConsultAppQueryService consultAppQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping
    public ApiResponse<List<ConsultResponse>> searchConsultsByParent(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        List<ConsultResponse> responses = consultAppQueryService.searchConsults(userKey, schoolYear);

        return ApiResponse.ok(responses);
    }
}

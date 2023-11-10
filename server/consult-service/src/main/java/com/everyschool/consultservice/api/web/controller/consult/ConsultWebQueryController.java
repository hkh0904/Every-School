package com.everyschool.consultservice.api.web.controller.consult;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.Result;
import com.everyschool.consultservice.api.web.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.api.web.service.consult.ConsultWebQueryService;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import com.everyschool.consultservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/consult-service/v1/web/{schoolYear}/schools/{schoolId}/consults")
public class ConsultWebQueryController {

    private final ConsultWebQueryService consultWebQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping
    public ApiResponse<Result<ConsultResponse>> searchConsults(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @RequestParam Integer status
    ) {
        validateStatusCode(status);

        String userKey = tokenUtils.getUserKey();

        List<ConsultResponse> responses = consultWebQueryService.searchConsults(userKey, schoolYear, schoolId, status);

        return ApiResponse.ok(Result.of(responses));
    }

    private void validateStatusCode(int status) {
        ProgressStatus.getText(status);
    }
}

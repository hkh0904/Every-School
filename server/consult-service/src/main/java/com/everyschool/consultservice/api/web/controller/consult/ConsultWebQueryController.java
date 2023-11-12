package com.everyschool.consultservice.api.web.controller.consult;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.Result;
import com.everyschool.consultservice.api.web.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.web.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.api.web.service.consult.ConsultWebQueryService;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import com.everyschool.consultservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 상담 웹 조회 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/consult-service/v1/web/{schoolYear}/schools/{schoolId}/consults")
public class ConsultWebQueryController {

    private final ConsultWebQueryService consultWebQueryService;
    private final TokenUtils tokenUtils;

    /**
     * 상담 목록 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param status     상담 진행 상태 코드
     * @return 조회된 상담 목록
     */
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

    /**
     * 상담 상세 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param consultId  상담 아이디
     * @return 조회된 상담 상세 내용
     */
    @GetMapping("/{consultId}")
    public ApiResponse<ConsultDetailResponse> searchConsult(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long consultId
    ) {

        ConsultDetailResponse response = consultWebQueryService.searchConsult(consultId);

        return ApiResponse.ok(response);
    }

    /**
     * 상담 진행 상태 코드 유효성 검증
     *
     * @param status 상담 진행 상태 코드
     */
    private void validateStatusCode(int status) {
        ProgressStatus.getText(status);
    }
}

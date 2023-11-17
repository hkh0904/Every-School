package com.everyschool.consultservice.api.app.controller.consult;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.app.controller.consult.response.ConsultDetailResponse;
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

/**
 * 앱 상담 조회용 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/consult-service/v1/app/{schoolYear}/schools/{schoolId}/consults")
public class ConsultAppQueryController {

    private final ConsultAppQueryService consultAppQueryService;
    private final TokenUtils tokenUtils;

    /**
     * 학부모용 상담 내역 목록 조회
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 상담 내역 목록
     */
    @GetMapping("/parent")
    public ApiResponse<List<ConsultResponse>> searchConsultsByParent(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        List<ConsultResponse> responses = consultAppQueryService.searchConsultsByParent(userKey, schoolYear, schoolId);

        return ApiResponse.ok(responses);
    }

    /**
     * 학부보용 상담 내역 상세 조회
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param consultId  상담 아이디
     * @return 조회된 상담 상세 내역
     */
    @GetMapping("/parent/{consultId}")
    public ApiResponse<ConsultDetailResponse> searchConsultByParent(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long consultId
    ) {

        ConsultDetailResponse response = consultAppQueryService.searchConsult(consultId);

        return ApiResponse.ok(response);
    }

    /**
     * 교직원용 상담 내역 목록 조회
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 상담 내역 목록
     */
    @GetMapping("/teacher")
    public ApiResponse<List<ConsultResponse>> searchConsultsByTeacher(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        List<ConsultResponse> responses = consultAppQueryService.searchConsultsByTeacher(userKey, schoolYear, schoolId);

        return ApiResponse.ok(responses);
    }

    /**
     * 교직원용 상담 내역 상세 조회
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param consultId  상담 아이디
     * @return 조회된 상담 상세 내역
     */
    @GetMapping("/teacher/{consultId}")
    public ApiResponse<ConsultDetailResponse> searchConsultByTeacher(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long consultId
    ) {

        ConsultDetailResponse response = consultAppQueryService.searchConsult(consultId);

        return ApiResponse.ok(response);
    }
}

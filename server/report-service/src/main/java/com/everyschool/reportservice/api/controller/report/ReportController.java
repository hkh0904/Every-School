package com.everyschool.reportservice.api.controller.report;

import com.everyschool.reportservice.api.ApiResponse;
import com.everyschool.reportservice.api.controller.report.request.ReportRequest;
import com.everyschool.reportservice.api.controller.report.response.FileResponse;
import com.everyschool.reportservice.api.controller.report.response.ReportResponse;
import com.everyschool.reportservice.api.controller.report.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/report-service/v1")
public class ReportController {

    /**
     * 신고 등록 API
     *
     * @return 등록 처리
     */
    @PostMapping("/report")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> createReport(@RequestBody ReportRequest request) {
        return ApiResponse.ok("신고가 접수 되었습니다.");
    }

    /**
     * 내 신고 내역 조회 API
     *
     * @return 신고 리스트
     */
    @GetMapping("/report")
    public ApiResponse<List<ReportResponse>> searchReports() {
    // header에서 토큰을 가져와 그대로 user-service에 요청
    // 요청해서 학생, 선생님, 행정인지 받아온다
    // member pk 를 받아온다

    // 이시나리오는 행정실 직원이 학교 전체 신고 내역 조회

        FileResponse f1 = FileResponse.builder()
                .uploadFileName("코피 사진")
                .build();

        FileResponse f2 = FileResponse.builder()
                .uploadFileName("멍 사진")
                .build();

        List<FileResponse> fList = new ArrayList<>();
        fList.add(f1);
        fList.add(f2);

        ReportResponse r1 = ReportResponse.builder()
                .reportType("학교폭력")
                .user(
                        new UserResponse(3, 1, "홍경환")
                )
                .reportTitle("이예리가 때림요")
                .reportWho("이예리가 때림요")
                .reportWhen("이예리가 때림요")
                .reportWhere("이예리가 때림요")
                .reportWhat("이예리가 때림요")
                .reportHow("이예리가 때림요")
                .reportWhy("이예리가 때림요")
                .uploadFiles(fList)
                .result("처리중")
                .createdDate(LocalDateTime.now())
                .build();

        ReportResponse r2 = ReportResponse.builder()
                .reportType("교권 침해")
                .user(
                        new UserResponse(3, 1, "홍경환")
                )
                .reportTitle("막말 오연주 어머님 신고합니다")
                .reportWho("막말 오연주 어머님 신고합니다")
                .reportWhen("막말 오연주 어머님 신고합니다")
                .reportWhere("막말 오연주 어머님 신고합니다")
                .reportWhat("막말 오연주 어머님 신고합니다")
                .reportHow("막말 오연주 어머님 신고합니다")
                .reportWhy("막말 오연주 어머님 신고합니다")
                .uploadFiles(new ArrayList<>())
                .result("처리중")
                .createdDate(LocalDateTime.now())
                .build();

        List<ReportResponse> responseList = new ArrayList<>();
        responseList.add(r1);
        responseList.add(r2);

        return ApiResponse.ok(responseList);
    }
}

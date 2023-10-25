package com.everyschool.consultservice.api.controller.consult;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.controller.consult.request.CreateConsultRequest;
import com.everyschool.consultservice.api.controller.consult.request.CreateConsultScheduleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/consult-service/v1")
public class ConsultController {

    @PostMapping("/consult")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Long> createConsult(@RequestBody @Valid CreateConsultRequest request) {
        // TODO: 2023-10-24 상담 신청
        return ApiResponse.created(1L);
    }

    @PostMapping("/consult/schedule")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Long> createConsultSchedule(@RequestBody @Valid CreateConsultScheduleRequest request) {

        // TODO: 2023-10-24 상담 일정 등록
        return ApiResponse.created(1L);
    }

    // TODO: 2023-10-24 상담 가능 일정 조회

    // TODO: 2023-10-24 상담 상태 변경

    // TODO: 2023-10-24 상담 삭제

    // TODO: 2023-10-24 상담 결과 기록
}

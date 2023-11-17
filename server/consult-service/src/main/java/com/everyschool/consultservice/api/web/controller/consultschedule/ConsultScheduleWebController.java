package com.everyschool.consultservice.api.web.controller.consultschedule;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.web.controller.consultschedule.request.EditDescriptionRequest;
import com.everyschool.consultservice.api.web.controller.consultschedule.request.EditScheduleRequest;
import com.everyschool.consultservice.api.web.controller.consultschedule.response.ConsultScheduleResponse;
import com.everyschool.consultservice.api.web.service.consultschedule.ConsultScheduleWebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 웹 상담 스케줄 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/consult-service/v1/app/{schoolYear}/schools/{schoolId}/consult-schedules")
public class ConsultScheduleWebController {

    private final ConsultScheduleWebService consultScheduleWebService;

    @PatchMapping("/{consultScheduleId}/description")
    public ApiResponse<ConsultScheduleResponse> editDescription(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long consultScheduleId,
        @Valid @RequestBody EditDescriptionRequest request
    ) {
        ConsultScheduleResponse response = consultScheduleWebService.editDescription(consultScheduleId, request.getDescription());

        return ApiResponse.ok(response);
    }

    @PatchMapping("/{consultScheduleId}/schedules")
    public ApiResponse<ConsultScheduleResponse> editSchedules(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long consultScheduleId,
        @Valid @RequestBody EditScheduleRequest request
    ) {
        ConsultScheduleResponse response = consultScheduleWebService.editSchedule(consultScheduleId, request.toDto());

        return ApiResponse.ok(response);
    }

}

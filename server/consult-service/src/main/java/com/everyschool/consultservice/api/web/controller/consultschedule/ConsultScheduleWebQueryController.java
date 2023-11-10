package com.everyschool.consultservice.api.web.controller.consultschedule;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.web.controller.consultschedule.response.ConsultScheduleResponse;
import com.everyschool.consultservice.api.web.service.consultschedule.ConsultScheduleWebQueryService;
import com.everyschool.consultservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/consult-service/v1/app/{schoolYear}/schools/{schoolId}/consult-schedules")
public class ConsultScheduleWebQueryController {

    private final ConsultScheduleWebQueryService consultScheduleWebQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping
    public ApiResponse<ConsultScheduleResponse> searchMyConsultSchedule(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {

        String userKey = tokenUtils.getUserKey();

        ConsultScheduleResponse response = consultScheduleWebQueryService.searchMyConsultSchedule(userKey);

        return ApiResponse.ok(response);
    }
}

package com.everyschool.consultservice.api.app.controller.consultschedule;

import com.everyschool.consultservice.api.ApiResponse;
import com.everyschool.consultservice.api.app.controller.consultschedule.response.ConsultScheduleResponse;
import com.everyschool.consultservice.api.app.service.consultschedule.ConsultScheduleAppQueryService;
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
public class ConsultScheduleAppQueryController {

    private final ConsultScheduleAppQueryService consultScheduleAppQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping("/{schoolClassId}")
    public ApiResponse<ConsultScheduleResponse> searchConsultInfo(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long schoolClassId
    ) {
        ConsultScheduleResponse response = consultScheduleAppQueryService.searchTeacherConsultSchedule(schoolClassId);

        return ApiResponse.ok(response);
    }
}

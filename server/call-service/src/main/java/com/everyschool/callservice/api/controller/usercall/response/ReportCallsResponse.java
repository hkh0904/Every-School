package com.everyschool.callservice.api.controller.usercall.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportCallsResponse {

    private Long userCallId;
    private String type;
    private String reportedName;
    private LocalDateTime reportedTime;

    @Builder
    public ReportCallsResponse(Long userCallId, String type, String reportedName, LocalDateTime reportedTime) {
        this.userCallId = userCallId;
        this.type = type;
        this.reportedName = reportedName;
        this.reportedTime = reportedTime;
    }
}

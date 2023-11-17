package com.everyschool.callservice.api.controller.usercall.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportCallsResponse {

    @JsonProperty(namespace = "reportId")
    private Long userCallId;
    private String type;
    private String reportedName;
    private LocalDate reportedDate;

    @Builder
    public ReportCallsResponse(Long userCallId, String reportedName, LocalDateTime reportedDate) {
        this.userCallId = userCallId;
        this.reportedName = reportedName;
        this.reportedDate = reportedDate.toLocalDate();
        this.type = "통화";
    }
}

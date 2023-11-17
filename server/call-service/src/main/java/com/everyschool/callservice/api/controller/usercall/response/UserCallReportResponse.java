package com.everyschool.callservice.api.controller.usercall.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCallReportResponse {

    private String overallSentiment;
    private Float overallNeutral;
    private Float overallPositive;
    private Float overallNegative;
    private Boolean isBad;

    private List<UserCallDetailsResponse> details;

    @Builder
    public UserCallReportResponse(String overallSentiment, Float overallNeutral, Float overallPositive,
                                  Float overallNegative, Boolean isBad) {
        this.overallSentiment = overallSentiment;
        this.overallNeutral = overallNeutral;
        this.overallPositive = overallPositive;
        this.overallNegative = overallNegative;
        this.isBad = isBad;
    }
}

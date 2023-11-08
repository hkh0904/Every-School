package com.everyschool.callservice.api.controller.donotdisturb.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoNotDisturbResponse {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @JsonProperty("isActivate")
    private boolean isActivate;

    @Builder
    public DoNotDisturbResponse(LocalDateTime startTime, LocalDateTime endTime, boolean isActivate) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActivate = isActivate;
    }
}

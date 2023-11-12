package com.everyschool.callservice.api.controller.donotdisturb.response;

import com.everyschool.callservice.domain.donotdisturb.DoNotDisturb;
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

    private Long doNotDisturbId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @JsonProperty("isActivate")
    private boolean isActivate;

    @Builder
    public DoNotDisturbResponse(Long doNotDisturbId, LocalDateTime startTime, LocalDateTime endTime, boolean isActivate) {
        this.doNotDisturbId = doNotDisturbId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActivate = isActivate;
    }

    public static DoNotDisturbResponse of(DoNotDisturb doNotDisturb){
        return DoNotDisturbResponse.builder()
                .doNotDisturbId(doNotDisturb.getId())
                .startTime(doNotDisturb.getStartTime())
                .endTime(doNotDisturb.getEndTime())
                .isActivate(doNotDisturb.getIsActivate())
                .build();
    }
}

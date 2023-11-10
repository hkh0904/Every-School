package com.everyschool.callservice.api.controller.donotdisturb.request;

import com.everyschool.callservice.api.service.donotdisturb.dto.DoNotDisturbDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DoNotDisturbRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @JsonProperty("isActivate")
    private boolean isActivate;

    @Builder
    public DoNotDisturbRequest(LocalDateTime startTime, LocalDateTime endTime, boolean isActivate) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActivate = isActivate;
    }

    public DoNotDisturbDto toDto() {
        return DoNotDisturbDto.builder()
                .startTime(this.startTime)
                .endTime(this.endTime)
                .isActivate(this.isActivate)
                .build();
    }
}

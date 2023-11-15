package com.everyschool.userservice.api.app.service.user.dto;

import com.everyschool.userservice.api.app.controller.user.response.ParentInfoResponse;
import lombok.Builder;
import lombok.Data;

@Data
public class ParentInfoResponseDto {

    private ParentInfoResponse parentInfoResponse;
    private int status; //1:자녀 등록됨, 0: 자녀 승인 대기, -1 : 자녀 등록 필요

    @Builder
    private ParentInfoResponseDto(ParentInfoResponse parentInfoResponse, int status) {
        this.parentInfoResponse = parentInfoResponse;
        this.status = status;
    }
}

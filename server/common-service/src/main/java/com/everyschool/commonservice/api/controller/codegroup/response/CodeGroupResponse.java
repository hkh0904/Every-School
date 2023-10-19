package com.everyschool.commonservice.api.controller.codegroup.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CodeGroupResponse {

    private Long groupId;
    private String name;

    @Builder
    public CodeGroupResponse(Long groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }
}

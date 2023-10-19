package com.everyschool.commonservice.api.controller.codegroup.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCodeGroupResponse {

    private Long groupId;
    private String name;
    private LocalDateTime createdDate;

    @Builder
    public CreateCodeGroupResponse(Long groupId, String name, LocalDateTime createdDate) {
        this.groupId = groupId;
        this.name = name;
        this.createdDate = createdDate;
    }
}

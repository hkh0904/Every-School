package com.everyschool.userservice.api.controller.codegroup.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCodeGroupResponse {

    private Integer groupId;
    private String groupName;
    private LocalDateTime createdDate;

    @Builder
    public CreateCodeGroupResponse(Integer groupId, String groupName, LocalDateTime createdDate) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.createdDate = createdDate;
    }
}

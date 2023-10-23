package com.everyschool.userservice.api.controller.codegroup.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CodeGroupResponse {

    private Integer groupId;
    private String groupName;
    private boolean isDeleted;

    @Builder
    public CodeGroupResponse(Integer groupId, String groupName, boolean isDeleted) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.isDeleted = isDeleted;
    }
}

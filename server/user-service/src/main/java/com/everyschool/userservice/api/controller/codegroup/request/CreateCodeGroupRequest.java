package com.everyschool.userservice.api.controller.codegroup.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCodeGroupRequest {

    private String groupName;

    @Builder
    private CreateCodeGroupRequest(String groupName) {
        this.groupName = groupName;
    }
}

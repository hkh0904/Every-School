package com.everyschool.userservice.api.controller.codegroup.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreateCodeGroupRequest {

    @NotBlank(message = "그룹 이름은 필수입니다.")
    private String groupName;

    @Builder
    private CreateCodeGroupRequest(String groupName) {
        this.groupName = groupName;
    }
}

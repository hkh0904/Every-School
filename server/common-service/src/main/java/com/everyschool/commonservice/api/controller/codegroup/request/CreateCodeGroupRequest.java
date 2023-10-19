package com.everyschool.commonservice.api.controller.codegroup.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCodeGroupRequest {

    private String name;

    @Builder
    private CreateCodeGroupRequest(String name) {
        this.name = name;
    }
}

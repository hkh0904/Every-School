package com.everyschool.commonservice.api.controller.codedetail.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCodeDetailRequest {

    private String name;

    @Builder
    private CreateCodeDetailRequest(String name) {
        this.name = name;
    }
}

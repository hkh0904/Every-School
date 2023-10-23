package com.everyschool.userservice.api.controller.codedetail.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCodeDetailRequest {

    private String codeName;

    @Builder
    private CreateCodeDetailRequest(String codeName) {
        this.codeName = codeName;
    }
}

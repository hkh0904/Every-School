package com.everyschool.userservice.api.controller.codedetail.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class CreateCodeDetailRequest {

    @NotEmpty(message = "코드 이름은 필수입니다.")
    private String codeName;

    @Builder
    private CreateCodeDetailRequest(String codeName) {
        this.codeName = codeName;
    }
}

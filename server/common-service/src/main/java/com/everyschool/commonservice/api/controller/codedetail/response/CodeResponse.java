package com.everyschool.commonservice.api.controller.codedetail.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CodeResponse {

    private Long codeId;
    private String codeName;

    @Builder
    public CodeResponse(Long codeId, String codeName) {
        this.codeId = codeId;
        this.codeName = codeName;
    }
}

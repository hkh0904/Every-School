package com.everyschool.userservice.api.controller.codedetail.respnse;

import lombok.Builder;
import lombok.Data;

@Data
public class CodeDetailResponse {

    private Integer codeId;
    private String codeName;
    private Boolean isDeleted;

    @Builder
    public CodeDetailResponse(Integer codeId, String codeName, Boolean isDeleted) {
        this.codeId = codeId;
        this.codeName = codeName;
        this.isDeleted = isDeleted;
    }
}

package com.everyschool.userservice.api.controller.codedetail.respnse;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CodeResponse {

    private Integer groupId;
    private String groupName;
    private List<CodeDetailResponse> codes;

    @Builder
    private CodeResponse(Integer groupId, String groupName, List<CodeDetailResponse> codes) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.codes = codes;
    }
}

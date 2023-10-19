package com.everyschool.commonservice.api.controller.codedetail.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CodeDetailResponse {

    private Long groupId;
    private String groupName;
    private List<CodeResponse> codes;

    @Builder
    public CodeDetailResponse(Long groupId, String groupName, List<CodeResponse> codes) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.codes = codes;
    }
}

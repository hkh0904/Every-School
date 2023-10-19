package com.everyschool.commonservice.api.controller.codedetail.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCodeDetailResponse {

    private Long codeId;
    private String groupName;
    private String codeName;
    private LocalDateTime createdDate;

    @Builder
    private CreateCodeDetailResponse(Long codeId, String groupName, String codeName, LocalDateTime createdDate) {
        this.codeId = codeId;
        this.groupName = groupName;
        this.codeName = codeName;
        this.createdDate = createdDate;
    }
}

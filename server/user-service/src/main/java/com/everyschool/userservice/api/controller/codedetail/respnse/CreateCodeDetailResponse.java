package com.everyschool.userservice.api.controller.codedetail.respnse;

import com.everyschool.userservice.domain.codedetail.CodeDetail;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCodeDetailResponse {

    private Integer groupId;
    private String groupName;
    private Integer codeId;
    private String codeName;
    private LocalDateTime createdDate;

    @Builder
    public CreateCodeDetailResponse(Integer groupId, String groupName, Integer codeId, String codeName, LocalDateTime createdDate) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.codeId = codeId;
        this.codeName = codeName;
        this.createdDate = createdDate;
    }

    public static CreateCodeDetailResponse of(CodeDetail codeDetail) {
        return CreateCodeDetailResponse.builder()
            .groupId(codeDetail.getGroup().getId())
            .groupName(codeDetail.getGroup().getGroupName())
            .codeId(codeDetail.getId())
            .codeName(codeDetail.getCodeName())
            .createdDate(codeDetail.getCreatedDate())
            .build();
    }
}

package com.everyschool.commonservice.api.controller.codedetail.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RemoveCodeDetailResponse {

    private Long codeId;
    private String name;
    private LocalDateTime removedDate;

    @Builder
    public RemoveCodeDetailResponse(Long codeId, String name, LocalDateTime removedDate) {
        this.codeId = codeId;
        this.name = name;
        this.removedDate = removedDate;
    }
}

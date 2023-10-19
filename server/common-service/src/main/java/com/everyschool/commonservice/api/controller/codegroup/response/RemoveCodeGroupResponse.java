package com.everyschool.commonservice.api.controller.codegroup.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RemoveCodeGroupResponse {

    private Long groupId;
    private String name;
    private LocalDateTime removedDate;

    @Builder
    public RemoveCodeGroupResponse(Long groupId, String name, LocalDateTime removedDate) {
        this.groupId = groupId;
        this.name = name;
        this.removedDate = removedDate;
    }
}

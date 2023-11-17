package com.everyschool.userservice.api.controller.codegroup.response;

import com.everyschool.userservice.domain.codegroup.CodeGroup;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RemoveCodeGroupResponse {

    private Integer groupId;
    private String groupName;
    private LocalDateTime removedDate;

    @Builder
    public RemoveCodeGroupResponse(Integer groupId, String groupName, LocalDateTime removedDate) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.removedDate = removedDate;
    }

    public static RemoveCodeGroupResponse of(CodeGroup codeGroup) {
        return RemoveCodeGroupResponse.builder()
            .groupId(codeGroup.getId())
            .groupName(codeGroup.getGroupName())
            .removedDate(codeGroup.getLastModifiedDate())
            .build();
    }
}

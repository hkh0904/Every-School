package com.everyschool.callservice.api.controller.usercall.request;

import com.everyschool.callservice.api.service.usercall.dto.CreateUserCallDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RecordStopRequest {

    @NotNull
    private String cname;

    @NotNull
    private String uid;

    @NotNull
    private String resourceId;

    @NotNull
    private String sid;

    @NotNull
    private String otherUserKey;

    @NotNull
    private String sender;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    @Builder
    private RecordStopRequest(String cname, String uid, String resourceId, String sid, String otherUserKey,
                             String sender, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.cname = cname;
        this.uid = uid;
        this.resourceId = resourceId;
        this.sid = sid;
        this.otherUserKey = otherUserKey;
        this.sender = sender;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public CreateUserCallDto toDto() {
        return CreateUserCallDto.builder()
                .sender(this.sender)
                .startDateTime(this.startDateTime)
                .endDateTime(this.endDateTime)
                .build();
    }

}

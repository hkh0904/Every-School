package com.everyschool.callservice.api.controller.call.request;

import com.everyschool.callservice.api.service.call.dto.CreateCallDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateCallRequest {

    @NotNull
    private String otherUserKey;

    @NotNull
    private String sender;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    @NotNull
    @Size(min = 10, max = 200)
    private String uploadFileName;

    @NotNull
    @Size(min = 10, max = 200)
    private String storeFileName;

    @Builder
    private CreateCallRequest(String otherUserKey, String sender, LocalDateTime startDateTime, LocalDateTime endDateTime,
                              String uploadFileName, String storeFileName, Boolean isBad) {
        this.otherUserKey = otherUserKey;
        this.sender = sender;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public CreateCallDto toDto() {
        return CreateCallDto.builder()
                .sender(this.sender)
                .startDateTime(this.startDateTime)
                .endDateTime(this.endDateTime)
                .uploadFileName(this.uploadFileName)
                .storeFileName(this.storeFileName)
                .build();
    }
}

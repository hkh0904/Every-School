package com.everyschool.callservice.api.controller.usercall.request;

import com.everyschool.callservice.api.service.call.dto.CreateUserCallDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateUserCallRequest {

    @NotNull
    private String otherUserKey;

    @NotNull
    private String sender;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDateTime;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDateTime;

    @NotNull
    private MultipartFile file;

    @Builder
    private CreateUserCallRequest(String otherUserKey, String sender, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                  MultipartFile file) {
        this.otherUserKey = otherUserKey;
        this.sender = sender;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.file = file;
    }

    public CreateUserCallDto toDto() {
        return CreateUserCallDto.builder()
                .sender(this.sender)
                .startDateTime(this.startDateTime)
                .endDateTime(this.endDateTime)
                .build();
    }
}

package com.everyschool.callservice.api.service.call.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateUserCallDto {
    private Long otherUserId;
    private Long teacherId;
    private String sender;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String uploadFileName;
    private String storeFileName;
    private Boolean isBad;

    @Builder
    private CreateUserCallDto(Long otherUserId, Long teacherId, String sender, LocalDateTime startDateTime,
                              LocalDateTime endDateTime, String uploadFileName, String storeFileName, Boolean isBad) {
        this.otherUserId = otherUserId;
        this.teacherId = teacherId;
        this.sender = sender;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.isBad = isBad;
    }
}

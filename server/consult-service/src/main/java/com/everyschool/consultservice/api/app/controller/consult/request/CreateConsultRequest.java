package com.everyschool.consultservice.api.app.controller.consult.request;

import com.everyschool.consultservice.api.app.service.consult.dto.CreateConsultDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateConsultRequest {

    @NotBlank(message = "교직원 고유키는 필수입니다.")
    private String teacherKey;

    @NotBlank(message = "자녀 고유키는 필수입니다.")
    private String studentKey;

    @NotNull(message = "상담 종류는 필수입니다.")
    private Integer typeId;

    @NotNull(message = "상담 시간은 필수입니다.")
    private LocalDateTime consultDateTime;

    @NotBlank(message = "상담 사유는 필수입니다.")
    @Size(max = 500, message = "상담 사유는 최대 500자입니다.")
    private String message;

    @Builder
    private CreateConsultRequest(String teacherKey, String studentKey, Integer typeId, LocalDateTime consultDateTime, String message) {
        this.teacherKey = teacherKey;
        this.studentKey = studentKey;
        this.typeId = typeId;
        this.consultDateTime = consultDateTime;
        this.message = message;
    }

    public CreateConsultDto toDto() {
        return CreateConsultDto.builder()
            .teacherKey(this.teacherKey)
            .studentKey(this.studentKey)
            .typeId(this.typeId)
            .consultDateTime(this.consultDateTime)
            .message(this.message)
            .build();
    }
}

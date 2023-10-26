package com.everyschool.alarmservice.api.controller.alarm.request;

import com.everyschool.alarmservice.api.service.alarm.dto.CreateAlarmDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class SendAlarmRequest {

    @NotBlank(message = "알림 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "알림 내용은 필수입니다.")
    private String content;

    @NotNull(message = "학년도는 필수입니다.")
    private Integer schoolYear;

    @NotNull(message = "받는 사람은 필수입니다.")
    private List<String> recipientUserKeys;

    @Builder
    private SendAlarmRequest(String title, String content, Integer schoolYear, List<String> recipientUserKeys) {
        this.title = title;
        this.content = content;
        this.schoolYear = schoolYear;
        this.recipientUserKeys = recipientUserKeys;
    }

    public CreateAlarmDto toDto() {
        return CreateAlarmDto.builder()
            .title(this.title)
            .content(this.content)
            .schoolYear(this.schoolYear)
            .recipientUserKeys(this.recipientUserKeys)
            .build();
    }
}

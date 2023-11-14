package com.everyschool.alarmservice.api.controller.client.request;

import com.everyschool.alarmservice.api.service.alarm.dto.CreateAlarmDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class SendTeacherAlarmRequest {

    @NotBlank(message = "알림 보낼 선생님 유저키")
    private String teacherUserKey;

    @NotBlank(message = "알림 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "알림 내용은 필수입니다.")
    private String content;

    @NotBlank(message = "타입은 필수입니다.")
    private String type;

    @NotBlank(message = "알림 생성할 객체 ID")
    private Long objectId;

    @NotNull(message = "학년도는 필수입니다.")
    private Integer schoolYear;

    @Builder
    public SendTeacherAlarmRequest(String teacherUserKey, String title, String content, String type, Long objectId,
                                   Integer schoolYear) {
        this.teacherUserKey = teacherUserKey;
        this.title = title;
        this.content = content;
        this.type = type;
        this.objectId = objectId;
        this.schoolYear = schoolYear;
    }


    public CreateAlarmDto toDto() {
        return CreateAlarmDto.builder()
            .title(this.title)
            .content(this.content)
            .type(this.type)
            .schoolYear(this.schoolYear)
            .recipientUserKeys(List.of(this.teacherUserKey))
            .build();
    }
}

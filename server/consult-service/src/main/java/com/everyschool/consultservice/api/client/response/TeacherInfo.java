package com.everyschool.consultservice.api.client.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherInfo {

    private Long userId;
    private String name;
    private Long schoolClassId;
    private String userKey;

    @Builder
    public TeacherInfo(Long userId, String name, Long schoolClassId, String userKey) {
        this.userId = userId;
        this.name = name;
        this.schoolClassId = schoolClassId;
        this.userKey = userKey;
    }
}

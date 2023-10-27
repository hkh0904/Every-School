package com.everyschool.schoolservice.api.controller.school.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SchoolDetailResponse {

    private Long schoolId;
    private String name;
    private String address;
    private String url;
    private String tel;
    private LocalDateTime openDate;

    @Builder
    public SchoolDetailResponse(Long schoolId, String name, String address, String url, String tel, LocalDateTime openDate) {
        this.schoolId = schoolId;
        this.name = name;
        this.address = address;
        this.url = url;
        this.tel = tel;
        this.openDate = openDate;
    }
}

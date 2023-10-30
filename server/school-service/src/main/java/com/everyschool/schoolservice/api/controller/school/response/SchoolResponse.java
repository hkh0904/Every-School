package com.everyschool.schoolservice.api.controller.school.response;

import lombok.Builder;
import lombok.Data;

@Data
public class SchoolResponse {

    private Long schoolId;
    private String name;
    private String address;

    @Builder
    public SchoolResponse(Long schoolId, String name, String address) {
        this.schoolId = schoolId;
        this.name = name;
        this.address = address;
    }
}

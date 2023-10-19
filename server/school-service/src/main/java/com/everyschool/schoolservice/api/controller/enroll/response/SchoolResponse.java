package com.everyschool.schoolservice.api.controller.enroll.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchoolResponse {

    private String name;
    private String address;
    private String url;
    private String tel;

    @Builder
    public SchoolResponse(String name, String address, String url, String tel) {
        this.name = name;
        this.address = address;
        this.url = url;
        this.tel = tel;
    }
}

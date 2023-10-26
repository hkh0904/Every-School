package com.everyschool.schoolservice.api.controller.school.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchoolResponse {
    private Long id;
    private String name;
    private String address;
    private String url;
    private String tel;

    @Builder
    public SchoolResponse(Long id, String name, String address, String url, String tel) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.url = url;
        this.tel = tel;
    }
}

package com.everyschool.consultservice.api.web.controller.consultschedule.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditDescriptionRequest {

    private String description;

    @Builder
    private EditDescriptionRequest(String description) {
        this.description = description;
    }
}

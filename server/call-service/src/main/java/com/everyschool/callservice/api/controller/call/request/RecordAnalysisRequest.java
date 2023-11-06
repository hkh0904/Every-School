package com.everyschool.callservice.api.controller.call.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class RecordAnalysisRequest {

    @NotNull
    private MultipartFile file;

    @Builder
    public RecordAnalysisRequest(MultipartFile file) {
        this.file = file;
    }
}

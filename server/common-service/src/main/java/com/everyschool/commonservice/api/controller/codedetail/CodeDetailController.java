package com.everyschool.commonservice.api.controller.codedetail;

import com.everyschool.commonservice.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/common-service/codes")
public class CodeDetailController {

    public ApiResponse<?> createCodeDetail() {
        return null;
    }

    public ApiResponse<?> searchCodeDetails() {
        return null;
    }

    public ApiResponse<?> editCodeDetail() {
        return null;
    }

    public ApiResponse<?> removeCodeDetail() {
        return null;
    }
}

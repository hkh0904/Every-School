package com.everyschool.callservice.api.controller.donotdisturb;

import com.everyschool.callservice.api.ApiResponse;
import com.everyschool.callservice.api.controller.donotdisturb.request.DoNotDisturbRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/call-service/v1/do-not-disturbs")
public class DoNotDisturbController {

    /**
     * (교사용) 방해 금지 타임 등록 API
     *
     * @return 요청 정보 리스트
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> createDoNotDisturb(@RequestBody DoNotDisturbRequest request) {
        return ApiResponse.ok("등록 완료");
    }
}

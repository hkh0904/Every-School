package com.everyschool.callservice.api.controller.donotdisturb;

import com.everyschool.callservice.api.ApiResponse;
import com.everyschool.callservice.api.controller.donotdisturb.request.DoNotDisturbRequest;
import com.everyschool.callservice.api.controller.donotdisturb.response.DoNotDisturbResponse;
import com.everyschool.callservice.api.service.donotdisturb.DoNotDisturbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/call-service/v1/do-not-disturbs")
public class DoNotDisturbController {

    private final DoNotDisturbService doNotDisturbService;

    /**
     * (교사용) 방해 금지 타임 등록 API
     *
     * @return 요청 정보 리스트
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DoNotDisturbResponse> createDoNotDisturb(@RequestBody DoNotDisturbRequest request,
                                                                @RequestHeader("Authorization") String token) {
        log.debug("call DoNotDisturbController#createDoNotDisturb");
        log.debug("request = {}", request);

        DoNotDisturbResponse response = doNotDisturbService.createDoNotDisturb(request.toDto(), token);
        return ApiResponse.ok(response);
    }

    /**
     * (교사용) 방해 금지 활성/비활성화 API
     *
     * @param doNotDisturbId
     * @return DoNotDisturbResponse
     */
    @PatchMapping("/on-off/{doNotDisturbId}")
    public ApiResponse<DoNotDisturbResponse> updateIsActivate(@PathVariable Long doNotDisturbId) {
        log.debug("call DoNotDisturbController#updateIsActivate");
        log.debug("doNotDisturbId = {}", doNotDisturbId);

        DoNotDisturbResponse response = doNotDisturbService.updateIsActivate(doNotDisturbId);

        return ApiResponse.ok(response);
    }

}

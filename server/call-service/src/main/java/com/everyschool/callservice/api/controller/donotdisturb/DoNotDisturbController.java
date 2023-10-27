package com.everyschool.callservice.api.controller.donotdisturb;

import com.everyschool.callservice.api.ApiResponse;
import com.everyschool.callservice.api.controller.donotdisturb.request.DoNotDisturbRequest;
import com.everyschool.callservice.api.controller.donotdisturb.response.DoNotDisturbResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/call-service/v1/donotdisturbs")
public class DoNotDisturbController {

    /**
     * (교사용) 방해 금지 타임 등록 API
     *
     * @return 요청 정보 리스트
     */
    @GetMapping("/")
    public ApiResponse<List<DoNotDisturbResponse>> searchDoNotDisturbs() {
        DoNotDisturbResponse res1 = DoNotDisturbResponse.builder()
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusDays(1))
                .isActivate(true)
                .build();
        DoNotDisturbResponse res2 = DoNotDisturbResponse.builder()
                .startTime(LocalDateTime.now().minusDays(5))
                .endTime(LocalDateTime.now().minusDays(3))
                .isActivate(true)
                .build();

        List<DoNotDisturbResponse> l = new ArrayList<>();
        l.add(res1);
        l.add(res2);

        return ApiResponse.ok(l);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> createDoNotDisturb(@RequestBody DoNotDisturbRequest request) {
        return ApiResponse.ok("등록 완료");
    }
}

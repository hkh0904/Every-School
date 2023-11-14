package com.everyschool.boardservice.api.app.controller.scrap;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.SliceResponse;
import com.everyschool.boardservice.api.app.controller.scrap.response.MyScrapResponse;
import com.everyschool.boardservice.api.app.service.scrap.ScrapAppQueryService;
import com.everyschool.boardservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board-service/v1/app/{schoolYear}/schools/{schoolId}/scraps")
public class ScrapAppQueryController {

    private final ScrapAppQueryService scrapAppQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping
    public ApiResponse<SliceResponse<MyScrapResponse>> searchMyScrap(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @RequestParam(defaultValue = "1") int page
    ) {
        String userKey = tokenUtils.getUserKey();

        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        SliceResponse<MyScrapResponse> response = scrapAppQueryService.searchMyScrap(userKey, pageRequest);
        log.debug("results={}", response);

        return ApiResponse.ok(response);
    }
}

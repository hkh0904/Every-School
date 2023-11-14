package com.everyschool.boardservice.api.app.controller.scrap;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.app.service.scrap.ScrapAppService;
import com.everyschool.boardservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board-service/v1/app/{schoolYear}/schools/{schoolId}/boards/{boardId}/scraps")
public class ScrapAppController {

    private final ScrapAppService scrapAppService;
    private final TokenUtils tokenUtils;

    @PostMapping
    public ApiResponse<Boolean> createScrap(
            @PathVariable Integer schoolYear,
            @PathVariable Long schoolId,
            @PathVariable Long boardId
    ) {
        String userKey = tokenUtils.getUserKey();

        boolean result = scrapAppService.createScrap(userKey, boardId);

        return ApiResponse.created(result);
    }

    @PostMapping("/unscraps")
    public ApiResponse<Boolean> unscraps(
            @PathVariable Integer schoolYear,
            @PathVariable Long schoolId,
            @PathVariable Long boardId
    ) {
        String userKey = tokenUtils.getUserKey();

        boolean result = scrapAppService.unscraps(userKey, boardId);

        return ApiResponse.ok(result);
    }
}

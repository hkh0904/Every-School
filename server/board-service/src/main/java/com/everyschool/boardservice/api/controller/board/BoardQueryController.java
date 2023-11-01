package com.everyschool.boardservice.api.controller.board;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.controller.board.response.NewFreeBoardResponse;
import com.everyschool.boardservice.api.service.board.BoardQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board-service/v1/schools/{schoolId}/boards")
public class BoardQueryController {

    private final BoardQueryService boardQueryService;

    @GetMapping("/new-free")
    public ApiResponse<List<NewFreeBoardResponse>> searchNewFreeBoards(@PathVariable Long schoolId) {
        log.debug("call BoardQueryController#searchNewFreeBoards");

        List<NewFreeBoardResponse> responses = boardQueryService.searchNewFreeBoards(schoolId);
        log.debug("results={}", responses);

        return ApiResponse.ok(responses);
    }
}

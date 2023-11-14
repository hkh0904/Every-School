package com.everyschool.boardservice.api.app.controller.board;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.app.controller.board.response.BoardResponse;
import com.everyschool.boardservice.api.app.service.board.MyBoardAppQueryService;
import com.everyschool.boardservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board-service/v1/app/{schoolYear}/schools/{schoolId}/my")
public class MyBoardAppController {

    private final MyBoardAppQueryService myBoardAppQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping("/boards")
    public ApiResponse<List<BoardResponse>> myBoards(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @RequestParam(defaultValue = "6001") Integer category
    ) {
        log.debug("[Controller] 나의 게시물 목록 조회. 요청 들어옴");
        String userKey = tokenUtils.getUserKey();

        List<BoardResponse> responses = myBoardAppQueryService.myBoards(userKey, category);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/comments")
    public ApiResponse<List<BoardResponse>> myComments(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        String userKey = tokenUtils.getUserKey();

        List<BoardResponse> responses = myBoardAppQueryService.myComments(userKey);

        return ApiResponse.ok(responses);
    }
}

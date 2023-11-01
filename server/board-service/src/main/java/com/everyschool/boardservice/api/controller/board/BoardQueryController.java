package com.everyschool.boardservice.api.controller.board;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.SliceResponse;
import com.everyschool.boardservice.api.controller.board.response.*;
import com.everyschool.boardservice.api.service.board.BoardQueryService;
import com.everyschool.boardservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board-service/v1/schools/{schoolId}/boards")
public class BoardQueryController {

    private final BoardQueryService boardQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping("/new-free")
    public ApiResponse<List<NewFreeBoardResponse>> searchNewFreeBoards(@PathVariable Long schoolId) {
        log.debug("call BoardQueryController#searchNewFreeBoards");

        List<NewFreeBoardResponse> responses = boardQueryService.searchNewFreeBoards(schoolId);
        log.debug("results={}", responses);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/frees")
    public ApiResponse<SliceResponse<BoardResponse>> searchFreeBoards(@PathVariable Long schoolId, @RequestParam(defaultValue = "1") int page) {
        log.debug("call BoardQueryController#searchFreeBoards");

        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        SliceResponse<BoardResponse> response = boardQueryService.searchFreeBoards(schoolId, pageRequest);
        log.debug("results={}", response);

        return ApiResponse.ok(response);
    }

    @GetMapping("/frees/{boardId}")
    public ApiResponse<FreeBoardDetailResponse> searchFreeBoard(@PathVariable Long schoolId, @PathVariable Long boardId) {
        log.debug("call BoardQueryController#searchFreeBoard");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        FreeBoardDetailResponse response = boardQueryService.searchFreeBoard(boardId, userKey);
        log.debug("results={}", response);

        return ApiResponse.ok(response);
    }

    @GetMapping("/new-notice")
    public ApiResponse<List<NewNoticeResponse>> searchNewNoticeBoards(@PathVariable Long schoolId) {
        log.debug("call BoardQueryController#searchNewNoticeBoards");

        List<NewNoticeResponse> responses = boardQueryService.searchNewNoticeBoards(schoolId);
        log.debug("results={}", responses);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/notices")
    public ApiResponse<SliceResponse<BoardResponse>> searchNoticeBoards(@PathVariable Long schoolId, @RequestParam(defaultValue = "1") int page) {
        log.debug("call BoardQueryController#searchNoticeBoards");

        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        SliceResponse<BoardResponse> response = boardQueryService.searchNoticeBoards(schoolId, pageRequest);
        log.debug("results={}", response);

        return ApiResponse.ok(response);
    }

    @GetMapping("/new-communication")
    public ApiResponse<List<NewCommunicationResponse>> searchNewCommunicationBoards(@PathVariable Long schoolId) {
        log.debug("call BoardQueryController#searchNewCommunicationBoards");

        List<NewCommunicationResponse> responses = boardQueryService.searchNewCommunicationBoards(schoolId);
        log.debug("results={}", responses);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/communications")
    public ApiResponse<SliceResponse<BoardResponse>> searchCommunicationBoards(@PathVariable Long schoolId, @RequestParam(defaultValue = "1") int page) {
        log.debug("call BoardQueryController#searchCommunicationBoards");

        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        SliceResponse<BoardResponse> response = boardQueryService.searchCommunicationBoards(schoolId, pageRequest);
        log.debug("results={}", response);

        return ApiResponse.ok(response);
    }
}

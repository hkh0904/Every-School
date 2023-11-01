package com.everyschool.boardservice.api.controller.board;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.controller.board.response.*;
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

    @GetMapping("/frees")
    public ApiResponse<List<FreeBoardResponse>> searchFreeBoards(@PathVariable Long schoolId) {
        log.debug("call BoardQueryController#searchFreeBoards");

        List<FreeBoardResponse> responses = boardQueryService.searchFreeBoards(schoolId);
        log.debug("results={}", responses);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/frees/{boardId}")
    public ApiResponse<FreeBoardDetailResponse> searchFreeBoard(@PathVariable Long schoolId, @PathVariable Long boardId) {
        log.debug("call BoardQueryController#searchFreeBoard");

        FreeBoardDetailResponse response = boardQueryService.searchFreeBoard(boardId);
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
    public ApiResponse<List<NoticeResponse>> searchNoticeBoards(@PathVariable Long schoolId) {
        log.debug("call BoardQueryController#searchNoticeBoards");

        List<NoticeResponse> responses = boardQueryService.searchNoticeBoards(schoolId);
        log.debug("results={}", responses);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/new-communication")
    public ApiResponse<List<NewCommunicationResponse>> searchNewCommunicationBoards(@PathVariable Long schoolId) {
        log.debug("call BoardQueryController#searchNewCommunicationBoards");

        List<NewCommunicationResponse> responses = boardQueryService.searchNewCommunicationBoards(schoolId);
        log.debug("results={}", responses);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/communications")
    public ApiResponse<List<CommunicationResponse>> searchCommunicationBoards(@PathVariable Long schoolId) {
        log.debug("call BoardQueryController#searchCommunicationBoards");

        List<CommunicationResponse> responses = boardQueryService.searchCommunicationBoards(schoolId);
        log.debug("results={}", responses);

        return ApiResponse.ok(responses);
    }
}

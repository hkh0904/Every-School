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

/**
 * 게시판 조회 API
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board-service/v1/app/{schoolYear}/schools/{schoolId}")
public class BoardQueryController {

    private final BoardQueryService boardQueryService;
    private final TokenUtils tokenUtils;

    /**
     * 자유게시판 게시물 목록 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param page       페이지 번호
     * @return 조회된 자유게시판 게시물 목록
     */
    @GetMapping("/free-boards")
    public ApiResponse<SliceResponse<BoardResponse>> searchFreeBoards(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @RequestParam(defaultValue = "1") int page
    ) {
        log.debug("call BoardQueryController#searchFreeBoards");

        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        SliceResponse<BoardResponse> response = boardQueryService.searchFreeBoards(schoolId, pageRequest);
        log.debug("results={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 자유게시판 게시물 상세 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param boardId    게시물 아이디
     * @return 조회된 게시물 정보
     */
    @GetMapping("/free-boards/{boardId}")
    public ApiResponse<FreeBoardDetailResponse> searchFreeBoard(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long boardId
    ) {
        log.debug("call BoardQueryController#searchFreeBoard");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        FreeBoardDetailResponse response = boardQueryService.searchFreeBoard(boardId, userKey);
        log.debug("results={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 자유게시판 새 게시물 목록 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 새 게시물 목록
     */
    @GetMapping("/free-boards/new")
    public ApiResponse<List<NewFreeBoardResponse>> searchNewFreeBoards(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        log.debug("call BoardQueryController#searchNewFreeBoards");

        List<NewFreeBoardResponse> responses = boardQueryService.searchNewFreeBoards(schoolId);
        log.debug("results={}", responses);

        return ApiResponse.ok(responses);
    }

    /**
     * 공지사항 게시물 목록 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param page       페이지 번호
     * @return 조회된 공지사항 게시물 목록
     */
    @GetMapping("/notice-boards")
    public ApiResponse<SliceResponse<BoardResponse>> searchNoticeBoards(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @RequestParam(defaultValue = "1") int page) {
        log.debug("call BoardQueryController#searchNoticeBoards");

        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        SliceResponse<BoardResponse> response = boardQueryService.searchNoticeBoards(schoolId, pageRequest);
        log.debug("results={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 공지사항 게시물 상세 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param boardId    게시물 아이디
     * @return 조회된 게시물 정보
     */
    @GetMapping("/notice-boards/{boardId}")
    public ApiResponse<BoardDetailResponse> searchNoticeBoard(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long boardId
    ) {
        log.debug("call BoardQueryController#searchNoticeBoard");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        BoardDetailResponse response = boardQueryService.searchBoard(boardId, userKey);
        log.debug("results={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 공지사항 새 게시물 목록 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 새 게시물 목록
     */
    @GetMapping("/notice-boards/new")
    public ApiResponse<List<NewNoticeResponse>> searchNewNoticeBoards(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        log.debug("call BoardQueryController#searchNewNoticeBoards");

        List<NewNoticeResponse> responses = boardQueryService.searchNewNoticeBoards(schoolId);
        log.debug("results={}", responses);

        return ApiResponse.ok(responses);
    }

    /**
     * 가정통신문 게시물 목록 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param page       페이지 번호
     * @return 조회된 가정통신문 게시물 목록
     */
    @GetMapping("/communication-boards")
    public ApiResponse<SliceResponse<BoardResponse>> searchCommunicationBoards(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @RequestParam(defaultValue = "1") int page
    ) {
        log.debug("call BoardQueryController#searchCommunicationBoards");

        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        SliceResponse<BoardResponse> response = boardQueryService.searchCommunicationBoards(schoolId, pageRequest);
        log.debug("results={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 가정통신문 게시물 상세 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param boardId    게시물 아이디
     * @return 조회된 게시물 정보
     */
    @GetMapping("/communication-boards/{boardId}")
    public ApiResponse<BoardDetailResponse> searchCommunicationBoard(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long boardId
    ) {
        log.debug("call BoardQueryController#searchCommunicationBoard");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        BoardDetailResponse response = boardQueryService.searchBoard(boardId, userKey);
        log.debug("results={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 가정통신문 새 게시물 목록 조회 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @return 조회된 새 게시물 목록
     */
    @GetMapping("/communication-boards/new")
    public ApiResponse<List<NewCommunicationResponse>> searchNewCommunicationBoards(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {
        log.debug("call BoardQueryController#searchNewCommunicationBoards");

        List<NewCommunicationResponse> responses = boardQueryService.searchNewCommunicationBoards(schoolId);
        log.debug("results={}", responses);

        return ApiResponse.ok(responses);
    }
}

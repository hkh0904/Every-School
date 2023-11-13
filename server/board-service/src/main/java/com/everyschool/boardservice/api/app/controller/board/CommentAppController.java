package com.everyschool.boardservice.api.app.controller.board;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.app.controller.board.request.CreateCommentRequest;
import com.everyschool.boardservice.api.app.controller.board.response.CreateCommentResponse;
import com.everyschool.boardservice.api.app.service.board.CommentAppService;
import com.everyschool.boardservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 앱 댓글 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board-service/v1/app/{schoolYear}/schools/{schoolId}/boards/{boardId}/comments")
public class CommentAppController {

    private final CommentAppService commentAppService;
    private final TokenUtils tokenUtils;

    /**
     * 댓글 작성 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param boardId    게시물 아이디
     * @param request    댓글 내용
     * @return 작성된 댓글
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateCommentResponse> createComment(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long boardId,
        @Valid @RequestBody CreateCommentRequest request
    ) {
        String userKey = tokenUtils.getUserKey();

        CreateCommentResponse response = commentAppService.createComment(userKey, boardId, null, request.getContent());

        return ApiResponse.created(response);
    }

    /**
     * 대댓글 작성 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param boardId    게시물 아이디
     * @param commentId  댓글 아이디
     * @param request    댓글 내용
     * @return 작성된 댓글
     */
    @PostMapping("/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateCommentResponse> createChildComment(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long boardId,
        @PathVariable Long commentId,
        @Valid @RequestBody CreateCommentRequest request
    ) {
        String userKey = tokenUtils.getUserKey();

        CreateCommentResponse response = commentAppService.createComment(userKey, boardId, commentId, request.getContent());

        return ApiResponse.created(response);
    }
}

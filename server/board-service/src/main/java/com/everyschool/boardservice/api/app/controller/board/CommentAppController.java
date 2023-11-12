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

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board-service/v1/app/{schoolYear}/schools/{schoolId}/boards/{boardId}/comments")
public class CommentAppController {

    private final CommentAppService commentAppService;
    private final TokenUtils tokenUtils;

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

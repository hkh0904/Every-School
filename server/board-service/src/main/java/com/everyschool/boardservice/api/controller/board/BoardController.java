package com.everyschool.boardservice.api.controller.board;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.controller.FileStore;
import com.everyschool.boardservice.api.controller.board.request.CreateBoardRequest;
import com.everyschool.boardservice.api.controller.board.response.CreateBoardResponse;
import com.everyschool.boardservice.api.service.board.BoardService;
import com.everyschool.boardservice.domain.board.UploadFile;
import com.everyschool.boardservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board-service/v1/schools/{schoolId}/boards")
public class BoardController {

    private final BoardService boardService;
    private final TokenUtils tokenUtils;
    private final FileStore fileStore;

    @PostMapping("/frees")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateBoardResponse> createFreeBoard(@PathVariable Long schoolId, @ModelAttribute CreateBoardRequest request) throws IOException {

        String userKey = tokenUtils.getUserKey();

        List<UploadFile> uploadFiles = fileStore.storeFiles(request.getFiles());

        CreateBoardResponse response = boardService.createFreeBoard(userKey, schoolId, request.toDto(uploadFiles));

        return ApiResponse.created(response);
    }



}

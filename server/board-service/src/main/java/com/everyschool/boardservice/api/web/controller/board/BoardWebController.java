package com.everyschool.boardservice.api.web.controller.board;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.controller.FileStore;
import com.everyschool.boardservice.api.controller.board.request.CreateBoardRequest;
import com.everyschool.boardservice.api.controller.board.response.CreateBoardResponse;
import com.everyschool.boardservice.api.web.service.board.BoardWebService;
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
@RequestMapping("/board-service/v1/web/{schoolYear}/schools/{schoolId}/boards")
public class BoardWebController {

    private final BoardWebService boardWebService;
    private final TokenUtils tokenUtils;
    private final FileStore fileStore;

    @PostMapping("/notices")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateBoardResponse> createNoticeBoard(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @ModelAttribute CreateBoardRequest request) throws IOException {

        String userKey = tokenUtils.getUserKey();

        List<UploadFile> uploadFiles = fileStore.storeFiles(request.getFiles());

        CreateBoardResponse response = boardWebService.createNoticeBoard(userKey, schoolId, request.toDto(uploadFiles));

        return ApiResponse.created(response);
    }

    @PostMapping("/communications")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateBoardResponse> createCommunicationBoard(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @ModelAttribute CreateBoardRequest request
    ) throws IOException {

        String userKey = tokenUtils.getUserKey();

        List<UploadFile> uploadFiles = fileStore.storeFiles(request.getFiles());

        CreateBoardResponse response = boardWebService.createCommunicationBoard(userKey, schoolId, request.toDto(uploadFiles));

        return ApiResponse.created(response);
    }
}

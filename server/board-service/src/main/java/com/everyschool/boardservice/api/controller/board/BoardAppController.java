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

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 게시판 App API
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board-service/v1/app/{schoolYear}/schools/{schoolId}")
public class BoardAppController {

    private final BoardService boardService;
    private final TokenUtils tokenUtils;
    private final FileStore fileStore;

    /**
     * 자유게시판 게시물 작성 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param request    요청 데이터
     * @return 등록된 게시물 정보
     * @throws IOException 파일 S3 업로드 중 문제 발생시 예외 발생
     */
    @PostMapping("/free-boards")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateBoardResponse> createFreeBoard(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @Valid @ModelAttribute CreateBoardRequest request
    ) throws IOException {

        String userKey = tokenUtils.getUserKey();

        List<UploadFile> uploadFiles = fileStore.storeFiles(request.getFiles());

        CreateBoardResponse response = boardService.createFreeBoard(userKey, schoolId, request.toDto(uploadFiles));

        return ApiResponse.created(response);
    }
}

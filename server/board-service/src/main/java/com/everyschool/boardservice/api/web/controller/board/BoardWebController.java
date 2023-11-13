package com.everyschool.boardservice.api.web.controller.board;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.FileStore;
import com.everyschool.boardservice.api.app.controller.board.request.CreateBoardRequest;
import com.everyschool.boardservice.api.app.controller.board.response.CreateBoardResponse;
import com.everyschool.boardservice.api.web.service.board.BoardWebService;
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
 * 웹 게시물 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board-service/v1/web/{schoolYear}/schools/{schoolId}")
public class BoardWebController {

    private final BoardWebService boardWebService;
    private final TokenUtils tokenUtils;
    private final FileStore fileStore;

    /**
     * 공지사항 등록 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param request    등록 정보
     * @return 등록된 공지사항 정보
     * @throws IOException 첨부파일을 S3 저장 오류 발생시
     */
    @PostMapping("/notice-boards")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateBoardResponse> createNoticeBoard(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @Valid @ModelAttribute CreateBoardRequest request
    ) throws IOException {

        String userKey = tokenUtils.getUserKey();

        List<UploadFile> uploadFiles = fileStore.storeFiles(request.getFiles());

        CreateBoardResponse response = boardWebService.createNoticeBoard(userKey, schoolId, request.toDto(uploadFiles));

        return ApiResponse.created(response);
    }

    /**
     * 가정통신문 등록 API
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param request    등록 정보
     * @return 등록된 공지사항 정보
     * @throws IOException 첨부파일을 S3 저장 오류 발생시
     */
    @PostMapping("/communication-boards")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateBoardResponse> createCommunicationBoard(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @Valid @ModelAttribute CreateBoardRequest request
    ) throws IOException {

        String userKey = tokenUtils.getUserKey();

        List<UploadFile> uploadFiles = fileStore.storeFiles(request.getFiles());

        CreateBoardResponse response = boardWebService.createCommunicationBoard(userKey, schoolId, request.toDto(uploadFiles));

        return ApiResponse.created(response);
    }
}

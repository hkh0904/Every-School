package com.everyschool.boardservice.api.controller.board;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.controller.board.request.CreateBoardRequest;
import com.everyschool.boardservice.api.controller.board.request.EditBoardRequest;
import com.everyschool.boardservice.api.controller.board.response.BoardListResponse;
import com.everyschool.boardservice.api.controller.board.response.BoardResponse;
import com.everyschool.boardservice.api.controller.board.response.CreateBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board-service/boards")
public class BoardController {

    @PostMapping("/{userKey}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateBoardResponse> createBoard(@Valid CreateBoardRequest request,
                                                        @PathVariable String userKey) {
        // TODO: 2023-10-19 교내 공지 작성
        List<MultipartFile> uploadFile = new ArrayList<>();
        CreateBoardResponse response = CreateBoardResponse.builder()
            .userName("김선생")
            .title("개교 기념일 안내")
            .content("학교 쉬어요~")
            .hit(0)
            .categoryName("교내 공지 공지")
            .categoryId(1L)
            .createdDate(LocalDateTime.of(2023, 10, 15, 10, 30))
            .uploadFiles(uploadFile)
            .build();
        return ApiResponse.created(response);
    }

    @GetMapping("/{schoolId}/{userKey}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<BoardListResponse>> searchBoards(@PathVariable Long schoolId,
                                                             @PathVariable String userKey,
                                                             @RequestParam Integer limit,
                                                             @RequestParam Long categoryId
    ) {
        // TODO: 2023-10-19 교내 공지 목록 조회

        BoardListResponse response1 = BoardListResponse.builder()
            .boardId(1L)
            .title("수업시간 외 학교 운동장 사용에 관한 공지")
            .createDate("2023.10.15")
            .build();
        BoardListResponse response2 = BoardListResponse.builder()
            .boardId(2L)
            .title("수업시간 외 학교 체육관 사용에 관한 공지")
            .createDate("2023.10.10")
            .build();

        List<BoardListResponse> responseList = List.of(response1, response2);
        return ApiResponse.ok(responseList);
    }

    @GetMapping("/{schoolId}/{userKey}/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<BoardResponse> searchBoard(@PathVariable Long schoolId,
                                                  @PathVariable String userKey,
                                                  @PathVariable String boardId) {
        // TODO: 2023-10-19 교내 공지 상세 조회
        //  학교 키로 공지글만 가져오기 
        BoardResponse response = BoardResponse.builder()
            .boardId(2L)
            .title("수업시간 외 학교 체육관 사용에 관한 공지")
            .content("사용 명부를 작성한 사람만 사용 가능")
            .userName("오체육")
            .createDate("2023.10.10 14:20")
            .uploadFiles(new ArrayList<>())
            .build();
        return ApiResponse.ok(response);
    }

    @PatchMapping("/{schoolId}/{userKey}/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<BoardResponse> editBoard(@PathVariable Long schoolId,
                                                @PathVariable String userKey,
                                                @PathVariable String boardId,
                                                @Valid EditBoardRequest request) {
        // TODO: 2023-10-19 교내 공지 상세 조회
        //  학교 키로 공지글만 가져오기
        BoardResponse response = BoardResponse.builder()
            .boardId(2L)
            .title("수업시간 외 학교 체육관 사용에 관한 공지")
            .content("사용 명부를 작성한 사람만 사용 가능")
            .userName("오체육")
            .createDate("2023.10.10 14:20")
            .uploadFiles(new ArrayList<>())
            .build();
        return ApiResponse.ok(response);
    }
}

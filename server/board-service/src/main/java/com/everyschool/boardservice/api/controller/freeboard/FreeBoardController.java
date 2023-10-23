package com.everyschool.boardservice.api.controller.freeboard;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.controller.freeboard.request.CreateFreeBoardRequest;
import com.everyschool.boardservice.api.controller.freeboard.response.CreateFreeBoardResponse;
import com.everyschool.boardservice.api.controller.freeboard.response.FreeBoardListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board-service/free-boards")
public class FreeBoardController {

    @PostMapping("/{userKey}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateFreeBoardResponse> createFreeBoard(@Valid CreateFreeBoardRequest request,
                                                                @PathVariable String userKey) {

        // TODO: 2023-10-20 자유게시판 글 작성 
        CreateFreeBoardResponse response = CreateFreeBoardResponse.builder()
            .title("급식 맛없다")
            .content("초밥 먹고싶다")
            .hit(0)
            .categoryId(2L)
            .createdDate(LocalDateTime.of(2023, 10, 15, 10, 30))
            .uploadFileNum(4)
            .build();
        return ApiResponse.created(response);
    }

    @GetMapping("/{schoolId}/{userKey}")
    public ApiResponse<List<FreeBoardListResponse>> searchFreeBoards(@PathVariable Long schoolId,
                                                                     @PathVariable String userKey,
                                                                     @RequestParam Integer limit,
                                                                     @RequestParam Long categoryId
    ) {
        // TODO: 2023-10-19 자유게시판 게시글 목록 조회

        FreeBoardListResponse response1 = FreeBoardListResponse.builder()
            .boardId(1L)
            .title("점심 맛있다")
            .createDate("2023.10.15")
            .commentNumber(4)
            .build();
        FreeBoardListResponse response2 = FreeBoardListResponse.builder()
            .boardId(2L)
            .title("3-9반 OOO대회 입상함")
            .createDate("2023.10.10")
            .commentNumber(24)
            .build();

        List<FreeBoardListResponse> responseList = List.of(response1, response2);
        return ApiResponse.ok(responseList);
    }

//    @GetMapping("/{schoolId}/{userKey}/{boardId}")
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse<BoardResponse> searchBoard(@PathVariable Long schoolId,
//                                                  @PathVariable String userKey,
//                                                  @PathVariable String boardId) {
//        // TODO: 2023-10-19 자유게시판 게시글 조회
//        //  학교 키로 공지글만 가져오기
//        BoardResponse response = BoardResponse.builder()
//            .boardId(2L)
//            .title("수업시간 외 학교 체육관 사용에 관한 공지")
//            .content("사용 명부를 작성한 사람만 사용 가능")
//            .userName("오체육")
//            .createDate("2023.10.10 14:20")
//            .uploadFiles(new ArrayList<>())
//            .build();
//        return ApiResponse.ok(response);
//    }
//
//    @PatchMapping("/{schoolId}/{userKey}/{boardId}")
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse<BoardResponse> editBoard(@PathVariable Long schoolId,
//                                                @PathVariable String userKey,
//                                                @PathVariable String boardId,
//                                                @Valid EditBoardRequest request) {
//        // TODO: 2023-10-19 자유게시판 게시글 수정
//        //  학교 키로 공지글만 가져오기
//        BoardResponse response = BoardResponse.builder()
//            .boardId(2L)
//            .title("수업시간 외 학교 체육관 사용에 관한 공지")
//            .content("사용 명부를 작성한 사람만 사용 가능")
//            .userName("오체육")
//            .createDate("2023.10.10 14:20")
//            .uploadFiles(new ArrayList<>())
//            .build();
//        return ApiResponse.ok(response);
//    }
//
//    @DeleteMapping("/{schoolId}/{userKey}/{boardId}")
//    public ApiResponse<String> deleteBoard(@PathVariable Long schoolId,
//                                           @PathVariable String userKey,
//                                           @PathVariable String boardId) {
//        // TODO: 2023-10-19 자유게시판 게시글 삭제
//        return ApiResponse.ok("삭제되었습니다.");
//    }
}

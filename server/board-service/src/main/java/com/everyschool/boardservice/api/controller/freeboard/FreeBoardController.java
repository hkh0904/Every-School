package com.everyschool.boardservice.api.controller.freeboard;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.controller.board.request.EditBoardRequest;
import com.everyschool.boardservice.api.controller.board.response.BoardResponse;
import com.everyschool.boardservice.api.controller.freeboard.request.CreateFreeBoardRequest;
import com.everyschool.boardservice.api.controller.freeboard.request.EditFreeBoardRequest;
import com.everyschool.boardservice.api.controller.freeboard.response.CommentResponse;
import com.everyschool.boardservice.api.controller.freeboard.response.CreateFreeBoardResponse;
import com.everyschool.boardservice.api.controller.freeboard.response.FreeBoardListResponse;
import com.everyschool.boardservice.api.controller.freeboard.response.FreeBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @GetMapping("/{schoolId}/{userKey}/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<FreeBoardResponse> searchFreeBoard(@PathVariable Long schoolId,
                                                          @PathVariable String userKey,
                                                          @PathVariable String boardId) {
        // TODO: 2023-10-19 자유게시판 게시글 조회
        //  학교 키로 공지글만 가져오기
        CommentResponse reComment1 = CommentResponse.builder()
            .userNumber(2)
            .createdDate("10/10 14:41")
            .content("차은우랑 결혼할거임")
            .reComment(new ArrayList<>())
            .build();

        CommentResponse reComment2 = CommentResponse.builder()
            .userNumber(1)
            .createdDate("10/10 14:51")
            .content("차은우는 뭔 죄")
            .reComment(new ArrayList<>())
            .build();

        CommentResponse reComment3 = CommentResponse.builder()
            .userNumber(0)
            .createdDate("10/10 14:55")
            .content("69세 차은우랑 결혼하신다함")
            .reComment(new ArrayList<>())
            .build();

        List<CommentResponse> reComment = List.of(reComment1, reComment2, reComment3);

        CommentResponse comment1 = CommentResponse.builder()
            .userNumber(1)
            .createdDate("10/10 14:31")
            .content("ㅋㅋㅋㅋㅋㅋㅋㅋㅇㅈㅇㅈ")
            .reComment(reComment)
            .build();

        List<CommentResponse> commentResponses = List.of(comment1);


        FreeBoardResponse response = FreeBoardResponse.builder()
            .boardId(2L)
            .title("1학년 2반 차은우 잘생김")
            .content("ㅇㅈ?")
            .userName("익명")
            .createDate("2023.10.10 14:20")
            .uploadFiles(new ArrayList<>())
            .comments(commentResponses)
            .build();
        return ApiResponse.ok(response);
    }

    @PatchMapping("/{schoolId}/{userKey}/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<FreeBoardResponse> editFreeBoard(@PathVariable Long schoolId,
                                                @PathVariable String userKey,
                                                @PathVariable String boardId,
                                                @Valid EditFreeBoardRequest request) {
        // TODO: 2023-10-19 자유게시판 게시글 수정
        //  학교 키로 공지글만 가져오기
        CommentResponse reComment1 = CommentResponse.builder()
            .userNumber(2)
            .createdDate("10/10 14:41")
            .content("차은우랑 결혼할거임")
            .reComment(new ArrayList<>())
            .build();

        CommentResponse reComment2 = CommentResponse.builder()
            .userNumber(1)
            .createdDate("10/10 14:51")
            .content("차은우는 뭔 죄")
            .reComment(new ArrayList<>())
            .build();

        CommentResponse reComment3 = CommentResponse.builder()
            .userNumber(0)
            .createdDate("10/10 14:55")
            .content("69세 차은우랑 결혼하신다함")
            .reComment(new ArrayList<>())
            .build();

        List<CommentResponse> reComment = List.of(reComment1, reComment2, reComment3);

        CommentResponse comment1 = CommentResponse.builder()
            .userNumber(1)
            .createdDate("10/10 14:31")
            .content("ㅋㅋㅋㅋㅋㅋㅋㅋㅇㅈㅇㅈ")
            .reComment(reComment)
            .build();

        List<CommentResponse> commentResponses = List.of(comment1);


        FreeBoardResponse response = FreeBoardResponse.builder()
            .boardId(2L)
            .title("1학년 2반 차은우 잘생김")
            .content("ㅇㅈ?")
            .userName("익명")
            .createDate("2023.10.10 14:20")
            .uploadFiles(new ArrayList<>())
            .comments(commentResponses)
            .build();
        return ApiResponse.ok(response);
    }

//    @DeleteMapping("/{schoolId}/{userKey}/{boardId}")
//    public ApiResponse<String> deleteBoard(@PathVariable Long schoolId,
//                                           @PathVariable String userKey,
//                                           @PathVariable String boardId) {
//        // TODO: 2023-10-19 자유게시판 게시글 삭제
//        return ApiResponse.ok("삭제되었습니다.");
//    }
}

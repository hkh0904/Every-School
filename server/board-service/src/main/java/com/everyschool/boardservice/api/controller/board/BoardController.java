package com.everyschool.boardservice.api.controller.board;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.controller.board.request.CreateBoardRequest;
import com.everyschool.boardservice.api.controller.board.response.CreateBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board-service/boards")
public class BoardController {

    @PostMapping("/{userKey}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateBoardResponse> createBoard(@RequestBody @Valid CreateBoardRequest request,
                                                        @PathVariable String userKey) {
        // TODO: 2023-10-19 게시글 작성
        CreateBoardResponse response = CreateBoardResponse.builder()
            .userName("김선생")
            .title("개교 기념일 안내")
            .content("학교 쉬어요~")
            .hit(0)
            .categoryName("학급 게시판")
            .createdDate(LocalDateTime.of(2023, 10, 15, 10, 30))
            .build();
        return ApiResponse.created(response);
    }


}

package com.everyschool.boardservice.api.controller.freeboard;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.controller.freeboard.request.CreateFreeBoardRequest;
import com.everyschool.boardservice.api.controller.freeboard.response.CreateFreeBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

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
}

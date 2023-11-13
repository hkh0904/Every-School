package com.everyschool.boardservice.api.web.controller.board;

import com.everyschool.boardservice.api.ApiResponse;
import com.everyschool.boardservice.api.web.controller.board.response.BoardResponse;
import com.everyschool.boardservice.api.web.service.board.BoardWebQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board-service/v1/web/{schoolYear}/schools/{schoolId}")
public class BoardWebQueryController {

    private final BoardWebQueryService boardWebQueryService;

    @GetMapping("/communication-boards")
    public ApiResponse<List<BoardResponse>> searchCommunicationBoards(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId
    ) {

        List<BoardResponse> response = boardWebQueryService.searchCommunicationBoards(schoolYear, schoolId);

        return ApiResponse.ok(response);
    }
}

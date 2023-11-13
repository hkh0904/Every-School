package com.everyschool.boardservice.api.web.service.board;

import com.everyschool.boardservice.api.client.UserServiceClient;
import com.everyschool.boardservice.api.client.response.UserInfo;
import com.everyschool.boardservice.api.web.controller.board.response.BoardResponse;
import com.everyschool.boardservice.domain.board.Board;
import com.everyschool.boardservice.domain.board.repository.BoardWebQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardWebQueryService {

    private final BoardWebQueryRepository boardWebQueryRepository;
    private final UserServiceClient userServiceClient;

    public List<BoardResponse> searchCommunicationBoards(Integer schoolYear, Long schoolId) {
        List<Board> boards = boardWebQueryRepository.findBySchoolYearAndSchoolId(schoolId);

        List<BoardResponse> responses = new ArrayList<>();
        for (Board board : boards) {
            UserInfo userInfo = userServiceClient.searchUserInfoById(board.getUserId());
            BoardResponse response = BoardResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .writer(userInfo.getUserName() + " 선생님")
                .lastModifiedDate(board.getLastModifiedDate())
                .build();
            responses.add(response);
        }

        return responses;
    }
}

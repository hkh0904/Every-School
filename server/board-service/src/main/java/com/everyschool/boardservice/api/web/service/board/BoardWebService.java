package com.everyschool.boardservice.api.web.service.board;

import com.everyschool.boardservice.api.client.UserServiceClient;
import com.everyschool.boardservice.api.client.response.UserInfo;
import com.everyschool.boardservice.api.app.controller.board.response.CreateBoardResponse;
import com.everyschool.boardservice.api.app.service.board.dto.CreateBoardDto;
import com.everyschool.boardservice.domain.board.Board;
import com.everyschool.boardservice.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.everyschool.boardservice.domain.board.Category.COMMUNICATION;
import static com.everyschool.boardservice.domain.board.Category.NOTICE;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardWebService {

    private final BoardRepository boardRepository;
    private final UserServiceClient userServiceClient;

    public CreateBoardResponse createNoticeBoard(String userKey, Long schoolId, CreateBoardDto dto) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        Board board = Board.createBoard(NOTICE.getCode(), schoolId, null, userInfo.getUserId(), dto.getTitle(), dto.getContent(), dto.getIsUsedComment(), dto.getUploadFiles());

        Board savedBoard = boardRepository.save(board);

        return CreateBoardResponse.of(savedBoard);
    }

    public CreateBoardResponse createCommunicationBoard(String userKey, Long schoolId, CreateBoardDto dto) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        Board board = Board.createBoard(COMMUNICATION.getCode(), schoolId, null, userInfo.getUserId(), dto.getTitle(), dto.getContent(), dto.getIsUsedComment(), dto.getUploadFiles());

        Board savedBoard = boardRepository.save(board);

        return CreateBoardResponse.of(savedBoard);
    }
}

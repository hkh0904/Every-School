package com.everyschool.boardservice.api.app.service.board;

import com.everyschool.boardservice.api.client.UserServiceClient;
import com.everyschool.boardservice.api.client.response.UserInfo;
import com.everyschool.boardservice.api.controller.board.response.CreateBoardResponse;
import com.everyschool.boardservice.api.app.service.board.dto.CreateBoardDto;
import com.everyschool.boardservice.domain.board.Board;
import com.everyschool.boardservice.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.everyschool.boardservice.domain.board.Category.*;

/**
 * 앱 게시판 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional
public class BoardAppService {

    private final BoardRepository boardRepository;
    private final UserServiceClient userServiceClient;

    /**
     * 자유 게시판 게시글 등록
     *
     * @param userKey  회원 고유키
     * @param schoolId 학교 아이디
     * @param dto      등록할 게시글 정보
     * @return 등록된 게시글 정보
     */
    public CreateBoardResponse createFreeBoard(String userKey, Long schoolId, CreateBoardDto dto) {
        //작성자 정보 조회
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        Board board = Board.createBoard(FREE.getCode(), schoolId, null, userInfo.getUserId(), dto.getTitle(), dto.getContent(), dto.getIsUsedComment(), dto.getUploadFiles());

        Board savedBoard = boardRepository.save(board);

        return CreateBoardResponse.of(savedBoard);
    }
}

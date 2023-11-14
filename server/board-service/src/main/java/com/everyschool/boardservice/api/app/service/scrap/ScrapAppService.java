package com.everyschool.boardservice.api.app.service.scrap;

import com.everyschool.boardservice.api.client.UserServiceClient;
import com.everyschool.boardservice.api.client.response.UserInfo;
import com.everyschool.boardservice.domain.board.Board;
import com.everyschool.boardservice.domain.board.Scrap;
import com.everyschool.boardservice.domain.board.repository.BoardRepository;
import com.everyschool.boardservice.domain.board.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.boardservice.error.ErrorMessage.NO_SUCH_BOARD;

@RequiredArgsConstructor
@Service
@Transactional
public class ScrapAppService {

    private final ScrapRepository scrapRepository;
    private final BoardRepository boardRepository;
    private final UserServiceClient userServiceClient;

    public boolean createScrap(String userKey, Long boardId) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        Board board = getBoard(boardId);

        Scrap scrap = Scrap.builder()
            .userId(userInfo.getUserId())
            .board(board)
            .build();

        scrapRepository.save(scrap);

        return true;
    }

    private Board getBoard(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_BOARD.getMessage());
        }
        return findBoard.get();
    }
}

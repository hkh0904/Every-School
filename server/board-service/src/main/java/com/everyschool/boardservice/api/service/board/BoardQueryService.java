package com.everyschool.boardservice.api.service.board;

import com.everyschool.boardservice.api.controller.FileStore;
import com.everyschool.boardservice.api.controller.board.response.*;
import com.everyschool.boardservice.domain.board.Board;
import com.everyschool.boardservice.domain.board.Comment;
import com.everyschool.boardservice.domain.board.repository.BoardQueryRepository;
import com.everyschool.boardservice.domain.board.repository.BoardRepository;
import com.everyschool.boardservice.domain.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.everyschool.boardservice.domain.board.Category.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardQueryService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final CommentRepository commentRepository;
    private final FileStore fileStore;

    public List<NewFreeBoardResponse> searchNewFreeBoards(Long schoolId) {

        List<NewFreeBoardResponse> responses = boardQueryRepository.findNewFreeBoardBySchoolId(schoolId, FREE);

        return responses;
    }

    public List<NewNoticeResponse> searchNewNoticeBoards(Long schoolId) {

        List<NewNoticeResponse> responses = boardQueryRepository.findNewNoticeBySchoolId(schoolId, NOTICE);

        return responses;
    }

    public List<NewCommunicationResponse> searchNewCommunicationBoards(Long schoolId) {

        List<NewCommunicationResponse> responses = boardQueryRepository.findNewCommunicationBySchoolId(schoolId, COMMUNICATION);

        return responses;
    }

    public List<FreeBoardResponse> searchFreeBoards(Long schoolId) {
        List<Board> boards = boardQueryRepository.findBoardBySchoolId(schoolId, FREE);

        List<FreeBoardResponse> responses = new ArrayList<>();

        for (Board board : boards) {
            String fullPath = "";

            if (!board.getFiles().isEmpty()) {
                fullPath = fileStore.getFullPath(board.getFiles().get(0).getUploadFile().getStoreFileName());
            }

            FreeBoardResponse response = FreeBoardResponse.of(board, fullPath);
            responses.add(response);
        }

        return responses;
    }

    public List<NoticeResponse> searchNoticeBoards(Long schoolId) {
        List<Board> boards = boardQueryRepository.findBoardBySchoolId(schoolId, NOTICE);

        return boards.stream()
            .map(NoticeResponse::of)
            .collect(Collectors.toList());
    }

    public List<CommunicationResponse> searchCommunicationBoards(Long schoolId) {
        List<Board> boards = boardQueryRepository.findBoardBySchoolId(schoolId, COMMUNICATION);

        return boards.stream()
            .map(CommunicationResponse::of)
            .collect(Collectors.toList());
    }

    public FreeBoardDetailResponse searchFreeBoard(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            throw new NoSuchElementException();
        }
        Board board = findBoard.get();

        List<String> imageUrls = board.getFiles().stream()
            .map(file -> fileStore.getFullPath(file.getUploadFile().getStoreFileName()))
            .collect(Collectors.toList());

        //게시판 pk로 모든 댓글 조회
        List<Comment> comments = commentRepository.findByBoardId(boardId);



        //부모가 존재하면 map에 key: 부모pk, value 댓글 삽입


        return null;
    }
}

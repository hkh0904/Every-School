package com.everyschool.boardservice.api.service.board;

import com.everyschool.boardservice.api.controller.FileStore;
import com.everyschool.boardservice.api.controller.board.response.*;
import com.everyschool.boardservice.domain.board.Board;
import com.everyschool.boardservice.domain.board.repository.BoardQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.everyschool.boardservice.domain.board.Category.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardQueryService {

    private final BoardQueryRepository boardQueryRepository;
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
}

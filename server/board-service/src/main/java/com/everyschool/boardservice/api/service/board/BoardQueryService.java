package com.everyschool.boardservice.api.service.board;

import com.everyschool.boardservice.api.controller.board.response.NewFreeBoardResponse;
import com.everyschool.boardservice.domain.board.repository.BoardQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.everyschool.boardservice.domain.board.Category.FREE;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardQueryService {

    private final BoardQueryRepository boardQueryRepository;

    public List<NewFreeBoardResponse> searchNewFreeBoards(Long schoolId) {

        List<NewFreeBoardResponse> responses = boardQueryRepository.findBySchoolId(schoolId, FREE);

        return responses;
    }
}

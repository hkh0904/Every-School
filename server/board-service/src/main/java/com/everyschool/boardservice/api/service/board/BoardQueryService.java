package com.everyschool.boardservice.api.service.board;

import com.everyschool.boardservice.api.SliceResponse;
import com.everyschool.boardservice.api.client.UserServiceClient;
import com.everyschool.boardservice.api.client.response.UserInfo;
import com.everyschool.boardservice.api.controller.FileStore;
import com.everyschool.boardservice.api.controller.board.response.*;
import com.everyschool.boardservice.domain.board.Board;
import com.everyschool.boardservice.domain.board.Comment;
import com.everyschool.boardservice.domain.board.repository.BoardQueryRepository;
import com.everyschool.boardservice.domain.board.repository.BoardRepository;
import com.everyschool.boardservice.domain.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.everyschool.boardservice.domain.board.Category.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardQueryService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final CommentRepository commentRepository;
    private final UserServiceClient userServiceClient;
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

    public SliceResponse<BoardResponse> searchFreeBoards(Long schoolId, Pageable pageable) {
        Slice<BoardResponse> result = boardQueryRepository.findBoardBySchoolId(schoolId, FREE, pageable);

        return new SliceResponse<>(result);
    }

    public SliceResponse<BoardResponse> searchNoticeBoards(Long schoolId, Pageable pageable) {
        Slice<BoardResponse> result = boardQueryRepository.findBoardBySchoolId(schoolId, NOTICE, pageable);

        return new SliceResponse<>(result);
    }

    public SliceResponse<BoardResponse> searchCommunicationBoards(Long schoolId, Pageable pageable) {
        Slice<BoardResponse> result = boardQueryRepository.findBoardBySchoolId(schoolId, COMMUNICATION, pageable);

        return new SliceResponse<>(result);
    }

    public FreeBoardDetailResponse searchFreeBoard(Long boardId, String userKey) {
        UserInfo userInfo = userServiceClient.searchByUserKey(userKey);

        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            throw new NoSuchElementException();
        }
        Board board = findBoard.get();

        List<String> imageUrls = board.getFiles().stream()
            .map(file -> fileStore.getFullPath(file.getUploadFile().getStoreFileName()))
            .collect(Collectors.toList());

        List<Comment> comments = commentRepository.findByBoardId(boardId);

        List<Comment> parentComments = new ArrayList<>();
        Map<Long, List<Comment>> commentMap = new HashMap<>();
        for (Comment comment : comments) {
            if (comment.getParent().getId() != null) {
                List<Comment> childComments = commentMap.getOrDefault(comment.getParent().getId(), List.of());
                childComments.add(comment);
                continue;
            }
            parentComments.add(comment);
        }

        List<FreeBoardDetailResponse.CommentVo> commentVos = new ArrayList<>();
        for (Comment comment : parentComments) {
            List<Comment> childComments = commentMap.getOrDefault(comment.getId(), List.of());
            List<FreeBoardDetailResponse.ReCommentVo> reComments = new ArrayList<>();
            for (Comment childComment : childComments) {
                FreeBoardDetailResponse.ReCommentVo vo = FreeBoardDetailResponse.ReCommentVo.builder()
                    .commentId(childComment.getId())
                    .sender(childComment.getAnonymousNum())
                    .content(childComment.getContent())
                    .depth(childComment.getDepth())
                    .isMine(childComment.getUserId().equals(userInfo.getUserId()))
                    .createdDate(childComment.getCreatedDate())
                    .build();
                reComments.add(vo);
            }

            FreeBoardDetailResponse.CommentVo.builder()
                .commentId(comment.getId())
                .sender(comment.getAnonymousNum())
                .content(comment.getContent())
                .depth(comment.getDepth())
                .isMine(comment.getUserId().equals(userInfo.getUserId()))
                .createdDate(comment.getCreatedDate())
                .reComments(reComments)
                .build();
        }

        return FreeBoardDetailResponse.of(board, imageUrls, commentVos);
    }

    public BoardDetailResponse searchBoard(Long boardId, String userKey) {
        return null;
    }
}

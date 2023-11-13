package com.everyschool.boardservice.api.app.service.board;

import com.everyschool.boardservice.api.SliceResponse;
import com.everyschool.boardservice.api.app.controller.board.response.*;
import com.everyschool.boardservice.api.client.UserServiceClient;
import com.everyschool.boardservice.api.client.response.UserInfo;
import com.everyschool.boardservice.api.FileStore;
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

import static com.everyschool.boardservice.api.app.controller.board.response.FreeBoardDetailResponse.*;
import static com.everyschool.boardservice.domain.board.Category.*;
import static com.everyschool.boardservice.error.ErrorMessage.NO_SUCH_BOARD;

/**
 * 앱 게시판 조회용 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardAppQueryService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final CommentRepository commentRepository;
    private final UserServiceClient userServiceClient;
    private final FileStore fileStore;

    /**
     * 신규 자유게시물 목록 조회
     *
     * @param schoolId 학교 아이디
     * @return 조회된 자유게시물 목록
     */
    public List<NewFreeBoardResponse> searchNewFreeBoards(Long schoolId) {

        List<NewFreeBoardResponse> responses = boardQueryRepository.findNewFreeBoardBySchoolId(schoolId, FREE);

        return responses;
    }

    /**
     * 신규 공지사항 목록 조회
     *
     * @param schoolId 학교 아이디
     * @return 조회된 공지사항 목록
     */
    public List<NewNoticeResponse> searchNewNoticeBoards(Long schoolId) {

        List<NewNoticeResponse> responses = boardQueryRepository.findNewNoticeBySchoolId(schoolId, NOTICE);

        return responses;
    }

    /**
     * 신규 가정통신문 목록 조회
     *
     * @param schoolId 학교 아이디
     * @return 조회된 공지사항 목록
     */
    public List<NewCommunicationResponse> searchNewCommunicationBoards(Long schoolId) {

        List<NewCommunicationResponse> responses = boardQueryRepository.findNewCommunicationBySchoolId(schoolId, COMMUNICATION);

        return responses;
    }

    /**
     * 자유게시판 목록 조회
     *
     * @param schoolId 학교 아이디
     * @param pageable 페이징 정보
     * @return 조회된 자유게시판 목록
     */
    public SliceResponse<BoardResponse> searchFreeBoards(Long schoolId, Pageable pageable) {
        Slice<BoardResponse> result = boardQueryRepository.findBoardBySchoolId(schoolId, FREE, pageable);

        return new SliceResponse<>(result);
    }

    /**
     * 공지사항 목록 조회
     *
     * @param schoolId 학교 아이디
     * @param pageable 페이징 정보
     * @return 조회된 공지사항 목록
     */
    public SliceResponse<BoardResponse> searchNoticeBoards(Long schoolId, Pageable pageable) {
        Slice<BoardResponse> result = boardQueryRepository.findBoardBySchoolId(schoolId, NOTICE, pageable);

        return new SliceResponse<>(result);
    }

    /**
     * 가정통신문 목록 조회
     *
     * @param schoolId 학교 아이디
     * @param pageable 페이징 정보
     * @return 조회된 가정통신문 목록
     */
    public SliceResponse<BoardResponse> searchCommunicationBoards(Long schoolId, Pageable pageable) {
        Slice<BoardResponse> result = boardQueryRepository.findBoardBySchoolId(schoolId, COMMUNICATION, pageable);

        return new SliceResponse<>(result);
    }

    /**
     * 자유게시판 상세 조회
     *
     * @param boardId 게시판 아이디
     * @param userKey 회원 고유키
     * @return 조회된 게시물 정보
     */
    public FreeBoardDetailResponse searchFreeBoard(Long boardId, String userKey) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        Board board = getBoardEntity(boardId);

        List<String> imageUrls = getFileUrls(board);

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

        List<CommentVo> commentVos = new ArrayList<>();

        for (Comment comment : parentComments) {
            List<Comment> childComments = commentMap.getOrDefault(comment.getId(), List.of());

            List<ReCommentVo> reComments = childComments.stream()
                .map(childComment -> ReCommentVo.builder()
                    .commentId(childComment.getId())
                    .sender(childComment.getAnonymousNum())
                    .content(childComment.getContent())
                    .depth(childComment.getDepth())
                    .isMine(childComment.getUserId().equals(userInfo.getUserId()))
                    .createdDate(childComment.getCreatedDate())
                    .build())
                .collect(Collectors.toList());

            CommentVo commentVo = CommentVo.builder()
                .commentId(comment.getId())
                .sender(comment.getAnonymousNum())
                .content(comment.getContent())
                .depth(comment.getDepth())
                .isMine(comment.getUserId().equals(userInfo.getUserId()))
                .createdDate(comment.getCreatedDate())
                .reComments(reComments)
                .build();

            commentVos.add(commentVo);
        }

        return of(board, imageUrls, commentVos);
    }

    // TODO: 2023-11-13 조회 구현
    public BoardDetailResponse searchBoard(Long boardId, String userKey) {
        return null;
    }

    /**
     * 게시물 아이디로 게시물 엔티티 조회
     *
     * @param boardId 게시물 아이디
     * @return 조회된 게시물
     */
    private Board getBoardEntity(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_BOARD.getMessage());
        }
        return findBoard.get();
    }

    /**
     * 파일 URL 조회
     *
     * @param board 게시물 엔티티
     * @return 조회된 게시물 URL 목록
     */
    private List<String> getFileUrls(Board board) {
        return board.getFiles().stream()
            .map(file -> fileStore.getFullPath(file.getUploadFile().getStoreFileName()))
            .collect(Collectors.toList());
    }
}

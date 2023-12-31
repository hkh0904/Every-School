package com.everyschool.boardservice.api.app.service.board;

import com.everyschool.boardservice.api.SliceResponse;
import com.everyschool.boardservice.api.app.controller.board.response.*;
import com.everyschool.boardservice.api.client.UserServiceClient;
import com.everyschool.boardservice.api.client.response.UserInfo;
import com.everyschool.boardservice.api.FileStore;
import com.everyschool.boardservice.domain.board.Board;
import com.everyschool.boardservice.domain.board.Category;
import com.everyschool.boardservice.domain.board.Comment;
import com.everyschool.boardservice.domain.board.Scrap;
import com.everyschool.boardservice.domain.board.repository.BoardQueryRepository;
import com.everyschool.boardservice.domain.board.repository.BoardRepository;
import com.everyschool.boardservice.domain.board.repository.CommentRepository;
import com.everyschool.boardservice.domain.board.repository.ScrapQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
@Slf4j
public class BoardAppQueryService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final CommentRepository commentRepository;
    private final UserServiceClient userServiceClient;
    private final FileStore fileStore;

    private final ScrapQueryRepository scrapQueryRepository;

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
     * @param userKey
     * @return 조회된 자유게시판 목록
     */
    public SliceResponse<BoardResponse> searchFreeBoards(Long schoolId, Pageable pageable, String userKey) {
        return getBoardResponse(schoolId, pageable, userKey, FREE);
    }

    /**
     * 공지사항 목록 조회
     *
     * @param schoolId 학교 아이디
     * @param pageable 페이징 정보
     * @return 조회된 공지사항 목록
     */
    public SliceResponse<BoardResponse> searchNoticeBoards(Long schoolId, Pageable pageable, String userKey) {
        return getBoardResponse(schoolId, pageable, userKey, NOTICE);
    }

    /**
     * 가정통신문 목록 조회
     *
     * @param schoolId 학교 아이디
     * @param pageable 페이징 정보
     * @return 조회된 가정통신문 목록
     */
    public SliceResponse<BoardResponse> searchCommunicationBoards(Long schoolId, Pageable pageable, String userKey) {
        return getBoardResponse(schoolId, pageable, userKey, COMMUNICATION);
    }

    @NotNull
    private SliceResponse<BoardResponse> getBoardResponse(Long schoolId, Pageable pageable, String userKey, Category category) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        Slice<BoardResponse> result = boardQueryRepository.findBoardBySchoolId(schoolId, category, pageable);

        for (BoardResponse board : result) {
            Scrap scrap = scrapQueryRepository.findByBoardAndUserId(board.getBoardId(), userInfo.getUserId());
            if (scrap != null) {
                board.setInMyScrap(true);
            }
        }

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
        log.debug("[Service] 자유게시판 상세조회. 게시판 Id = {}, 게시판 제목 = {}", board.getId(), board.getTitle());

        List<String> imageUrls = getFileUrls(board);

        List<Comment> comments = commentRepository.findByBoardId(boardId);
        log.debug("[Service] 자유게시판 상세조회. 모든 댓글 수 = {}", comments.size());

        List<Comment> parentComments = new ArrayList<>();

        Map<Long, List<Comment>> commentMap = new HashMap<>();
        for (Comment comment : comments) {
            log.debug("[Service] 자유게시판 상세조회. 댓글 Id = {}, 댓글 내용 = {}, 댓글 부모 = {}", comment.getId(), comment.getContent(), comment.getParent());
            if (comment.getParent() != null) {
                log.debug("[Service] 자유게시판 상세조회. 대댓임. 부모 아이디 = {}", comment.getParent().getId());

                if (!commentMap.containsKey(comment.getParent().getId())) {
                    commentMap.put(comment.getParent().getId(), new ArrayList<>());
                }
                commentMap.get(comment.getParent().getId()).add(comment);

//                List<Comment> childComments = commentMap.getOrDefault(comment.getParent().getId(), List.of());
//                childComments.add(comment);
                continue;
            }
            log.debug("[Service] 자유게시판 상세조회. 대댓 아님.");
            parentComments.add(comment);
        }

        List<CommentVo> commentVos = new ArrayList<>();

        for (Comment comment : parentComments) {
            List<Comment> childComments = commentMap.getOrDefault(comment.getId(), new ArrayList<>());

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

        Scrap scrap = scrapQueryRepository.findByBoardAndUserId(boardId, userInfo.getUserId());

        return of(board, userInfo.getUserId(), imageUrls, commentVos, scrap != null);
    }

    // TODO: 2023-11-13 조회 구현
    public BoardDetailResponse searchBoard(Long boardId, String userKey) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        Board board = getBoardEntity(boardId);

        List<String> imageUrls = getFileUrls(board);

        Scrap scrap = scrapQueryRepository.findByBoardAndUserId(boardId, userInfo.getUserId());

        return BoardDetailResponse.of(board, imageUrls, new ArrayList<>(), scrap != null);
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

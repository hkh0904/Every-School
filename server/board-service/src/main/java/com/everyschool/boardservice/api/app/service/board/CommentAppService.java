package com.everyschool.boardservice.api.app.service.board;

import com.everyschool.boardservice.api.app.controller.board.response.CreateCommentResponse;
import com.everyschool.boardservice.api.client.UserServiceClient;
import com.everyschool.boardservice.api.client.response.UserInfo;
import com.everyschool.boardservice.domain.board.Board;
import com.everyschool.boardservice.domain.board.Comment;
import com.everyschool.boardservice.domain.board.repository.BoardRepository;
import com.everyschool.boardservice.domain.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.boardservice.error.ErrorMessage.NO_SUCH_BOARD;
import static com.everyschool.boardservice.error.ErrorMessage.NO_SUCH_COMMENT;

/**
 * 앱 댓글 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional
public class CommentAppService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserServiceClient userServiceClient;

    /**
     * 댓글 작성
     *
     * @param userKey         회원 고유키
     * @param boardId         게시물 아이디
     * @param parentCommentId 부모 댓글 아이디
     * @param content         댓글 내용
     * @return 작성된 댓글 정보
     */
    public CreateCommentResponse createComment(String userKey, Long boardId, Long parentCommentId, String content) {
        //작성자 정보
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        //댓글 작성한 게시물
        Board board = getBoardEntity(boardId);

        //대댓글은 깊이 증가
        int depth = 0;
        Comment parentComment = null;
        if (parentCommentId != null) {
            parentComment = getCommentEntity(parentCommentId);
            depth = parentComment.getDepth() + 1;
        }

        int anonymousNum = getAnonymousNum(board, userInfo, boardId);

        Comment comment = Comment.builder()
            .content(content)
            .anonymousNum(anonymousNum)
            .depth(depth)
            .userId(userInfo.getUserId())
            .board(board)
            .parent(parentComment)
            .build();

        Comment savedComment = commentRepository.save(comment);

        return CreateCommentResponse.of(savedComment);
    }

    /**
     * 게시물 아이디로 게시물 엔티티 조회
     *
     * @param boardId 게시물 아이디
     * @return 조회된 게시물 엔티티
     */
    private Board getBoardEntity(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_BOARD.getMessage());
        }
        return findBoard.get();
    }

    /**
     * 댓글 아이디로 댓글 엔티티 조회
     *
     * @param commentId 댓글 아이디
     * @return 조회된 댓글 엔티티
     */
    private Comment getCommentEntity(Long commentId) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_COMMENT.getMessage());
        }
        return findComment.get();
    }

    /**
     * 익명 번호 부여
     *
     * @param board    게시물 엔티티
     * @param userInfo 회원 정보
     * @param boardId  게시물 아이디
     * @return 익명 번호
     */
    private int getAnonymousNum(Board board, UserInfo userInfo, Long boardId) {
        //게시물 작성자가 아닌 경우
        if (!board.getUserId().equals(userInfo.getUserId())) {
            //익명 번호 조회
            Optional<Integer> findAnonymousNum = commentRepository.findAnonymousNumByUserIdAndBoardId(userInfo.getUserId(), boardId);
            //존재하면 그 번호 적용
            if (findAnonymousNum.isPresent()) {
                return findAnonymousNum.get();
            }

            //아니면 하나 증가해서 적용
            board.increaseCommentNumber();
            return board.getCommentNumber();
        }
        return 0;
    }
}

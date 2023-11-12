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

@RequiredArgsConstructor
@Service
@Transactional
public class CommentAppService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserServiceClient userServiceClient;

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

        int anonymousNum = 0;

        //게시물 작성자가 아닌 경우
        if (!board.getUserId().equals(userInfo.getUserId())) {
            //익명 번호 조회
            Optional<Integer> findAnonymousNum = commentRepository.findAnonymousNumByUserIdAndBoardId(userInfo.getUserId(), boardId);
            //존재하면 그 번호 적용
            if (findAnonymousNum.isPresent()) {
                anonymousNum = findAnonymousNum.get();
            } else {
                //아니면 하나 증가해서 적용
                board.increaseCommentNumber();
                anonymousNum = board.getCommentNumber();
            }
        }

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

    private Board getBoardEntity(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_BOARD.getMessage());
        }
        return findBoard.get();
    }

    private Comment getCommentEntity(Long commentId) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_COMMENT.getMessage());
        }
        return findComment.get();
    }
}

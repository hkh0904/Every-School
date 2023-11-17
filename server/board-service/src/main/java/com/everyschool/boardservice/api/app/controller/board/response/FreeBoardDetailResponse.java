package com.everyschool.boardservice.api.app.controller.board.response;

import com.everyschool.boardservice.domain.board.Board;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FreeBoardDetailResponse {

    private Long boardId;
    private String title;
    private String content;
    private int commentCount;
    private int scrapCount;
    private Boolean inMyScrap;
    private Boolean isMine;
    private Boolean isUsedComment;
    private LocalDateTime createdDate;
    private List<String> imageUrls;
    private List<CommentVo> comments;

    @Builder
    private FreeBoardDetailResponse(Long boardId, String title, String content, int commentCount, int scrapCount, Boolean inMyScrap, Boolean isMine, Boolean isUsedComment, LocalDateTime createdDate, List<String> imageUrls, List<CommentVo> comments) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.scrapCount = scrapCount;
        this.inMyScrap = inMyScrap;
        this.isMine = isMine;
        this.isUsedComment = isUsedComment;
        this.createdDate = createdDate;
        this.imageUrls = imageUrls;
        this.comments = comments;
    }

    public static FreeBoardDetailResponse of(Board board, Long userId, List<String> imageUrls, List<CommentVo> comments, Boolean inMyScrap) {
        return FreeBoardDetailResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .commentCount(board.getCommentCount())
                .isMine(board.getUserId().equals(userId))
                .isUsedComment(board.getIsUsedComment())
                .scrapCount(board.getScrapCount())
                .inMyScrap(inMyScrap)
                .createdDate(board.getCreatedDate())
                .imageUrls(imageUrls)
                .comments(comments)
                .build();
    }

    @Data
    public static class CommentVo {

        private Long commentId;
        private int sender; // 0: 본인, 양수: 타인
        private String content;
        private int depth; // 1: 댓글, 2: 대댓글
        private Boolean isMine;
        private LocalDateTime createdDate;
        private List<ReCommentVo> reComments;

        @Builder
        public CommentVo(Long commentId, int sender, String content, int depth, Boolean isMine, LocalDateTime createdDate, List<ReCommentVo> reComments) {
            this.commentId = commentId;
            this.sender = sender;
            this.content = content;
            this.depth = depth;
            this.isMine = isMine;
            this.createdDate = createdDate;
            this.reComments = reComments;
        }
    }

    @Data
    public static class ReCommentVo {

        private Long commentId;
        private int sender; // 0: 본인, 양수: 타인
        private String content;
        private int depth; // 1: 댓글, 2: 대댓글
        private Boolean isMine;
        private LocalDateTime createdDate;

        @Builder
        public ReCommentVo(Long commentId, int sender, String content, int depth, Boolean isMine, LocalDateTime createdDate) {
            this.commentId = commentId;
            this.sender = sender;
            this.content = content;
            this.depth = depth;
            this.isMine = isMine;
            this.createdDate = createdDate;
        }
    }

}


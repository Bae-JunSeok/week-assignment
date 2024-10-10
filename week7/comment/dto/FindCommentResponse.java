package study.likelionbeweekly.week7.comment.dto;

import study.likelionbeweekly.week7.comment.Comment;

import java.util.List;

public record FindCommentResponse(List<FindComment> comments) {
    public static FindCommentResponse of (List<Comment> comments){
        return new FindCommentResponse(
                comments.stream()
                        .map(comment
                                -> new FindComment(
                                        comment.getId(),
                                        comment.getContent(),
                                        comment.getMember().getId(),
                                        comment.getMember().getName()))
                        .toList());
    }

    private record FindComment(Long id, String content, Long memberId, String memberName){
    }
}

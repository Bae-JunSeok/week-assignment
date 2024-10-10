package study.likelionbeweekly.week7.comment.dto;

public record FindCommentByIdResponse(Long id, String content, Long post_id, String createdAt) {
}

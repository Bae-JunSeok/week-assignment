package study.likelionbeweekly.week7.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*{
        "id": 1, // 게시글 ID
        "title": "게시글 제목",
        "content": "게시글 내용",
        "memberName": "작성자 이름"
        "createdAt" : "2024-06-08 13:12:11"
        }
        */
@Getter
@RequiredArgsConstructor
public class FindPostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String memberName;
    private final String createdAt;
}

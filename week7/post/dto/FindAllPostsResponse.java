package study.likelionbeweekly.week7.post.dto;

import study.likelionbeweekly.week7.post.Post;

import java.util.List;

public record FindAllPostsResponse(List<FindPost> posts) { //json으로 만들어줌.
    public static FindAllPostsResponse of (List<Post> posts){
        return new FindAllPostsResponse(
                posts.stream() // chain method
                        .map(post
                                -> new FindPost(
                                post.getId(),
                                post.getTitle(),
                                post.getMember().getName()))
                        .toList());
    }
    private record FindPost(Long id, String title, String memberName){ // 클래스 안에 클래스
    }
}

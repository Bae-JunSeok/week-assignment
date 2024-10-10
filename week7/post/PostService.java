package study.likelionbeweekly.week7.post;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.processing.Find;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.likelionbeweekly.week7.member.Member;
import study.likelionbeweekly.week7.member.MemberRepository;
import study.likelionbeweekly.week7.post.dto.CreatePostRequest;
import study.likelionbeweekly.week7.post.dto.FindAllPostsResponse;
import study.likelionbeweekly.week7.post.dto.FindPostResponse;
import study.likelionbeweekly.week7.post.dto.UpdatePostRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository; // jpa에서 알아서 스프링 빈으로 등록해줌.

    @Transactional
    public void createPost(Long member_Id, CreatePostRequest request){
        String createTitle = request.title();
        String createContent = request.content();
        Long memberId = member_Id;

        Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);

        Post post = new Post(createTitle, createContent, member);
        postRepository.save(post); // 아이디나 객체 자체를 반환할 수도 있다.
    }

    public FindAllPostsResponse findAllPosts(){
        List<Post> posts = postRepository.findAll(); // 페이징을 통해서 몇개씩 끊어서 불러올 수도 있다. List는 배열의 객체화
        /*
        if(posts.isEmpty()){
            log.error("등록된 게시글이 없습니다.");
            throw new IllegalArgumentException("등록된 게시글이 없습니다.");
        }
         */
        return FindAllPostsResponse.of(posts);
    }

    public FindPostResponse findPost(Long id){ // 뭐지? 왜 FindAllPosts 처럼 안만들까?
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new); // 나중에 custom exception 만들어야 함.
        FindPostResponse response = new FindPostResponse(
                post.getId(), post.getTitle(), post.getContent(), post.getMember().getName(), post.getCreatedAt().toString()
        );
        return response;
    }
    @Transactional
    public void updatePost(Long id, UpdatePostRequest request){
        String updateTitle = request.title();
        String updateContent = request.content();

        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new); // 나중에 custom exception을 만들어줄 것.

        post.setTitle(updateTitle);
        post.setContent(updateContent); // update 쿼리는 자동으로 날려줌.
        log.info("post update");
    }
    @Transactional
    public void deletePost(Long id){
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        post.setDeleted(true);
        log.info("post delete");
    }
}

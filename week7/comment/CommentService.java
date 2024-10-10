package study.likelionbeweekly.week7.comment;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.likelionbeweekly.week7.comment.dto.*;
import study.likelionbeweekly.week7.member.Member;
import study.likelionbeweekly.week7.member.MemberRepository;
import study.likelionbeweekly.week7.post.Post;
import study.likelionbeweekly.week7.post.PostRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(CreateCommentRequest request){
        String createContent = request.content();
        Long memberId = request.memberId();
        Long postId = request.postId();

        Member member = memberRepository.findById(memberId).orElseThrow(EntityExistsException::new);
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);

        Comment comment = new Comment(createContent, member, post);
        commentRepository.save(comment);
        log.info("comment created");
    }

    public FindCommentResponse findComment(FindCommentRequest request){
        Long postId = request.postId();
        List<Comment> comments = commentRepository.findByPostId(postId);
        return FindCommentResponse.of(comments);
    }

    public FindCommentByIdResponse findCommentById(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        FindCommentByIdResponse response = new FindCommentByIdResponse(
                comment.getId(), comment.getContent(), comment.getPost().getId(), comment.getCreatedAt().toString()
        );
        return response;
    }

    @Transactional
    public void updateComment(Long id, UpdateCommentRequest request){
        String updateContent = request.content();
        Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        comment.setContent(updateContent);
        log.info("comment update");
    }

    @Transactional
    public void deleteComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        comment.setDeleted(true);
        log.info("comment delete");
    }
}

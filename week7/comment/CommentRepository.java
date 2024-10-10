package study.likelionbeweekly.week7.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import study.likelionbeweekly.week7.member.Member;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}

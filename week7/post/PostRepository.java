package study.likelionbeweekly.week7.post;

import org.springframework.data.jpa.repository.JpaRepository;
// 데이터 베이스 접근 객체

//@Repository 해줘도 되고 안해줘도 됨.
public interface PostRepository extends JpaRepository<Post, Long> { //제네릭 정해진 것이라서 꼭 지켜야 함.

}

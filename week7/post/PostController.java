package study.likelionbeweekly.week7.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.likelionbeweekly.week7.jwt.JwtService;
import study.likelionbeweekly.week7.member.Member;
import study.likelionbeweekly.week7.post.dto.CreatePostRequest;
import study.likelionbeweekly.week7.post.dto.FindAllPostsResponse;
import study.likelionbeweekly.week7.post.dto.FindPostResponse;
import study.likelionbeweekly.week7.post.dto.UpdatePostRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor // final 키워드 무조건 초기화해주는 생성자.
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<String> create(@RequestHeader("Authorization") String token, @RequestBody CreatePostRequest request){ //@RequestBody 하는 이유:
        //Member member = jwtService.parse(token);
        //postService.createPost(member.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body("post created");
    }

    @GetMapping
    public ResponseEntity<FindAllPostsResponse> findAll(){
        FindAllPostsResponse response = postService.findAllPosts();
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FindPostResponse> find(@PathVariable(name = "id") Long id){
        FindPostResponse response = postService.findPost(id);
        return ResponseEntity.ok().body(response);
        }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody UpdatePostRequest request){ // http body에 넣었다는 것을 알려주기 위해서
        postService.updatePost(id, request);
        return ResponseEntity.ok().body("post update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
        postService.deletePost(id);
        return ResponseEntity.ok().body("post delete");
    }
}

package study.likelionbeweekly.week7.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.likelionbeweekly.week7.comment.dto.*;


@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateCommentRequest request){
        commentService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("comment created");
    }

    @GetMapping
    public ResponseEntity<FindCommentResponse> find(@RequestBody FindCommentRequest request){
        FindCommentResponse response = commentService.findComment(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}") // comment_id로 comment 찾기
    public ResponseEntity<FindCommentByIdResponse> findbyid(@PathVariable(name = "id") Long id){
        FindCommentByIdResponse response = commentService.findCommentById(id);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody UpdateCommentRequest request){
        commentService.updateComment(id, request);
        return ResponseEntity.ok().body("comment update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok().body("comment delete");
    }
}

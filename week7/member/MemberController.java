package study.likelionbeweekly.week7.member;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.likelionbeweekly.week7.jwt.JwtService;
import study.likelionbeweekly.week7.member.dto.JoinMemberRequest;
import study.likelionbeweekly.week7.member.dto.LoginMemberRequest;
import study.likelionbeweekly.week7.member.dto.UpdateMemberRequest;
import study.likelionbeweekly.week7.security.UserDetailsImpl;

import static study.likelionbeweekly.week7.utils.Constant.AUTHORIZATION_HEADER_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final JwtService jwtService;
    private final MemberService memberService;
    @PostMapping
    public ResponseEntity<String> join(@RequestBody @Valid JoinMemberRequest request){
        memberService.joinMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }

    @GetMapping
    public ResponseEntity<String> login(@RequestBody LoginMemberRequest request){
        Member member = memberService.loginMember(request);
        String email = member.getEmail();
        String jwt = jwtService.create(email);
        return ResponseEntity.ok()
                .header(AUTHORIZATION_HEADER_KEY.getContent(), jwt)
                .body("ok");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody UpdateMemberRequest request,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        Member member = userDetails.member();
        memberService.updateMember(member, request);
        return ResponseEntity.ok().body("ok");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
        memberService.deleteMember(id);
        return ResponseEntity.ok().body("ok");
    }
}



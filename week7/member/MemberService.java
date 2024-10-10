package study.likelionbeweekly.week7.member;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.likelionbeweekly.week7.member.dto.JoinMemberRequest;
import study.likelionbeweekly.week7.member.dto.LoginMemberRequest;
import study.likelionbeweekly.week7.member.dto.UpdateMemberRequest;
import study.likelionbeweekly.week7.member.MemberCustomException.*;


import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService { // parsing
    private final MemberRepository memberRepository; // 생성자 주입
    @Transactional
    public void joinMember(JoinMemberRequest request){
        String joinName = request.name();
        String joinEmail = request.email();
        String joinPassword = request.password();

        //Optional<Member> optionalMember = memberRepository.findByEmail(joinEmail);
        //if(optionalMember.isPresent()){
            //log.error("중복 이메일");
            //throw new IllegalArgumentException("중복 이메일");
        //}

        //checkDuplicateEmail(joinEmail);

        Member member = new Member(joinName, joinEmail, joinPassword);
        memberRepository.save(member);
    }
    @Transactional(readOnly = false)
    public Member loginMember(LoginMemberRequest request){
        String loginEmail = request.email();
        String loginPassword = request.password();

        Member member = memberRepository.findByEmail(loginEmail).orElseThrow(MemberNotFoundException::new);
        checkLoginEmailAndPassword(loginEmail, loginPassword, member);
        log.info("로그인 성공");
        return member;
    }

    private void checkLoginEmailAndPassword(String loginEmail, String loginPassword, Member member) {
        String email = member.getEmail();
        String password = member.getPassword();
        if (!Objects.equals(email, loginEmail) || !Objects.equals(password, loginPassword)) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }
    }
    
    @Transactional
    public void updateMember(Member member, UpdateMemberRequest request){
        //Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        String updateName = request.name();
        String updateEmail = request.email();
        String updatePassword = request.password();

        Optional<Member> optionalMember = memberRepository.findByEmail(updateEmail);
        checkDuplicateEmail(optionalMember);
        
        member.setName(updateName);
        member.setEmail(updateEmail);
        member.setPassword(updatePassword);
        log.info("업데이트 완료");
    }
    @Transactional
    public void deleteMember(Long id){
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        member.setDeleted(true);
        // 삭제 시간, Enum 상태 관리 등
    }

    private void checkDuplicateEmail(Optional<Member> optionalMember) {
        if (optionalMember.isPresent()) {
            throw new IllegalArgumentException("중복 이메일");
        }
    }
}

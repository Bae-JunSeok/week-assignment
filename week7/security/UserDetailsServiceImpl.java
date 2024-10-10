package study.likelionbeweekly.week7.security;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.likelionbeweekly.week7.member.Member;
import study.likelionbeweekly.week7.member.MemberRepository;
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    @Transactional(readOnly = true)
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException{
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        UserDetailsImpl userDetails = new UserDetailsImpl(member);
        return userDetails;
    }
}

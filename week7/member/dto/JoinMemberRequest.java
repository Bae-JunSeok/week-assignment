package study.likelionbeweekly.week7.member.dto;

import jakarta.validation.constraints.Size;

public record JoinMemberRequest(@Size(min = 2, max = 16, message = "이름은 2글자 이상 16자 이하이어야 합니다.") String name, String email, String password) {
// not null, not empty, not blank 등으로 검증 가능
// email
}

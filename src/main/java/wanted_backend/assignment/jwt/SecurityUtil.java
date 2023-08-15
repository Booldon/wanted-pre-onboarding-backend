package wanted_backend.assignment.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import wanted_backend.assignment.domain.Member;
import wanted_backend.assignment.repository.MemberRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final MemberRepository memberRepository;
    public Member getMemberFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()){
            String memberEmail = authentication.getName();
            Optional<Member> findMember = memberRepository.findByEmail(memberEmail);
                return findMember.get();
        }
        else throw new RuntimeException("인증정보가 없습니다.");
    }
}

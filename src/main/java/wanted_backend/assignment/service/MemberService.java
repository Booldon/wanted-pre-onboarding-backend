package wanted_backend.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted_backend.assignment.domain.Member;
import wanted_backend.assignment.domain.MemberRole;
import wanted_backend.assignment.exception.LoginFailException;
import wanted_backend.assignment.jwt.TokenProvider;
import wanted_backend.assignment.request.LoginRequest;
import wanted_backend.assignment.request.SignUpRequest;
import wanted_backend.assignment.repository.MemberRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final TokenProvider tokenProvider;

    public Member singUp(SignUpRequest request) {

        Member newUser = new Member();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        newUser.hashPassword(bCryptPasswordEncoder);
        newUser.setMemberRole(MemberRole.USER);
        return memberRepository.save(newUser);

    }


    public String login(LoginRequest loginRequest) {
        Optional<Member> findMember = memberRepository.findByEmail(loginRequest.getEmail());
        if(!findMember.orElseThrow(()-> new LoginFailException("해당 아이디가 존재하지 않음")).checkPassword(loginRequest.getPassword(),bCryptPasswordEncoder)){
            throw new LoginFailException("아이디와 비밀번호가 일치하지 않음");
        }
         Member loginMember = findMember.get();
        // 로그인 성공한 사용자에 대한 JWT 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginMember.getEmail(),
                null,
                Collections.singleton(new SimpleGrantedAuthority(loginMember.getMemberRole().name()))
        );
        String jwt = tokenProvider.createToken(authentication);

        log.info("jwt ={}",jwt);
        return jwt;

    }

    public Member findByEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        return findMember.get();
    }

}

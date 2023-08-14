package wanted_backend.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted_backend.assignment.domain.Authority;
import wanted_backend.assignment.domain.Member;
import wanted_backend.assignment.exception.LoginFailException;
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


    public Member singUp(SignUpRequest request) {

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Member newUser = new Member();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        newUser.hashPassword(bCryptPasswordEncoder);
        newUser.setAuthorities(Collections.singleton(authority));
        return memberRepository.save(newUser);

    }


    public Member login(LoginRequest loginForm) {
        Optional<Member> findUser = memberRepository.findByEmail(loginForm.getEmail());
        if(!findUser.orElseThrow(()-> new LoginFailException("해당 아이디가 존재하지 않음")).checkPassword(loginForm.getPassword(),bCryptPasswordEncoder)){
            throw new LoginFailException("아이디와 비밀번호가 일치하지 않음");
        }
        return findUser.get();
    }

    public Member findByEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        return findMember.get();
    }

}

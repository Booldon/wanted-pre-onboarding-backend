package wanted_backend.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted_backend.assignment.config.JwtProvider;
import wanted_backend.assignment.domain.Member;
import wanted_backend.assignment.exception.LoginFailException;
import wanted_backend.assignment.form.LoginForm;
import wanted_backend.assignment.form.MemberForm;
import wanted_backend.assignment.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService implements MemberServiceInterface {
    private final MemberRepository memberRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public Member singUp(MemberForm newUserForm) {

        Member newUser = new Member();
        newUser.setEmail(newUserForm.getEmail());
        newUser.setPassword(newUserForm.getPassword());
        newUser.hashPassword(bCryptPasswordEncoder);
        return memberRepository.save(newUser);

    }

    @Override
    public Member login(LoginForm loginForm) {
        Optional<Member> findUser = memberRepository.findByEmail(loginForm.getEmail());
        if(!findUser.orElseThrow(()-> new LoginFailException("해당 아이디가 존재하지 않음")).checkPassword(loginForm.getPassword(),bCryptPasswordEncoder)){
            throw new LoginFailException("아이디와 비밀번호가 일치하지 않음");
        }
        String token = jwtProvider.createToken(findUser.get());
        log.info("token = {}",token);

        return findUser.get();
    }
    @Override
    public Member findByEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        return findMember.get();
    }

}

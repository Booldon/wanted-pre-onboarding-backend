package wanted_backend.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted_backend.assignment.config.JwtProvider;
import wanted_backend.assignment.domain.User;
import wanted_backend.assignment.exception.LoginFailException;
import wanted_backend.assignment.form.LoginForm;
import wanted_backend.assignment.form.UserForm;
import wanted_backend.assignment.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    public User singUp(UserForm newUserForm) {

        User newUser = new User();
        newUser.setEmail(newUserForm.getEmail());
        newUser.setPassword(newUserForm.getPassword());
        newUser.hashPassword(bCryptPasswordEncoder);
        return userRepository.save(newUser);

    }

    public User login(LoginForm loginForm) {
        Optional<User> findUser = userRepository.findByEmail(loginForm.getEmail());
        if(!findUser.orElseThrow(()-> new LoginFailException("해당 아이디가 존재하지 않음")).checkPassword(loginForm.getPassword(),bCryptPasswordEncoder)){
            throw new LoginFailException("아이디와 비밀번호가 일치하지 않음");
        }
        String token = jwtProvider.createToken(String.format(findUser.get().getEmail()));
        log.info("token = {}",token);

        return findUser.get();
    }

}

package wanted_backend.assignment.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import wanted_backend.assignment.domain.Member;
import wanted_backend.assignment.dto.SignUpDto;
import wanted_backend.assignment.repository.MemberRepository;
@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    private static final String EMAIL = "sunder_111@naver.com";
    private static final String PASSWORD = "12345678";

    @Autowired

    private MemberService userService;

    @Autowired
    private MemberRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("비밀번호는 암호화되어야 한다.")
    void hashPassword() throws Exception {
        //given
        SignUpDto userForm = new SignUpDto();
        userForm.setEmail(EMAIL);
        userForm.setPassword(PASSWORD);

        //when
        Member user = userService.singUp(userForm);

        //then
        System.out.println("newUser pw = " + user.getPassword());
        Assertions.assertThat(bCryptPasswordEncoder.matches(PASSWORD, user.getPassword())).isTrue();
    }
}
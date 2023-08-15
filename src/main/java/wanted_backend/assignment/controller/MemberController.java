package wanted_backend.assignment.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wanted_backend.assignment.dto.TokenDto;
import wanted_backend.assignment.exception.LoginFailException;
import wanted_backend.assignment.jwt.JwtFilter;
import wanted_backend.assignment.dto.LoginDto;
import wanted_backend.assignment.dto.SignUpDto;
import wanted_backend.assignment.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public String signUp(@Valid @RequestBody SignUpDto signUpDto, BindingResult result) {

        if(result.hasErrors()) {
            throw new LoginFailException("로그인 형식에 맞지 않습니다.");
        }
        memberService.singUp(signUpDto);

        return signUpDto.getEmail()+","+signUpDto.getPassword();

    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> signUp(@Valid @RequestBody LoginDto loginDto, BindingResult result, HttpServletResponse response) {

        if(result.hasErrors()) {
            throw new LoginFailException("로그인 형식에 맞지 않습니다.");
        }
        String jwt = memberService.login(loginDto);
        response.addHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        log.info("response:{}",response.getHeader(JwtFilter.AUTHORIZATION_HEADER));

        return new ResponseEntity<>(new TokenDto(jwt), HttpStatus.OK);

    }


//    @PostConstruct
//    public void init() {
//        SignUpRequest signUpRequest = new SignUpRequest();
//        signUpRequest.setEmail("aa@bb.cc");
//        signUpRequest.setPassword("1234qwer");
//        memberService.singUp(signUpRequest);
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setEmail("aa@bb.cc");
//        loginRequest.setPassword("1234qwer");
//        memberService.login(loginRequest);
//
//    }

}
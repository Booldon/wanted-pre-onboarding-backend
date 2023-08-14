package wanted_backend.assignment.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wanted_backend.assignment.jwt.TokenProvider;
import wanted_backend.assignment.domain.Member;
import wanted_backend.assignment.request.LoginRequest;
import wanted_backend.assignment.request.SignUpRequest;
import wanted_backend.assignment.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider jwtProvider;

    @GetMapping("/signUp")
    public String showSignUp(@ModelAttribute SignUpRequest signUpRequest) {
        return "/users/signUpForm";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid SignUpRequest request, BindingResult result) {

        if(result.hasErrors()) {
            return "/users/signUpForm";
        }
        memberService.singUp(request);

        return "redirect:/";

    }

    @GetMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest) {
        return "/users/loginForm";
    }

    @PostMapping("/login")
    public String signUp(@Valid LoginRequest loginRequest, BindingResult result) {

        if(result.hasErrors()) {
            return "/users/loginForm";
        }
        Member loginMember = memberService.login(loginRequest);

        return "redirect:/";

    }

    @PostConstruct
    public void init() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("aa@bb.cc");
        loginRequest.setPassword("1234qwer");
        memberService.login(loginRequest);

    }

}
package wanted_backend.assignment.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wanted_backend.assignment.form.LoginForm;
import wanted_backend.assignment.form.MemberForm;
import wanted_backend.assignment.repository.MemberRepository;
import wanted_backend.assignment.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final MemberService userService;
    private final MemberRepository userRepository;

    @GetMapping("/signUp")
    public String showSignUp(@ModelAttribute MemberForm userForm) {
        return "/users/signUpForm";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid MemberForm userForm, BindingResult result) {

        if(result.hasErrors()) {
            return "/users/signUpForm";
        }
        userService.singUp(userForm);

        return "redirect:/";

    }

    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm) {
        return "/users/loginForm";
    }

    @PostMapping("/login")
    public String signUp(@Valid LoginForm loginForm, BindingResult result) {

        if(result.hasErrors()) {
            return "/users/loginForm";
        }
        userService.login(loginForm);
        return "redirect:/";

    }

    @PostConstruct
    public void init() {
        MemberForm userForm1 = new MemberForm();
        userForm1.setEmail("aa@bb.cc");
        userForm1.setPassword("12341234");
        MemberForm userForm2 = new MemberForm();
        userForm2.setEmail("dd@ee.ff");
        userForm2.setPassword("12341234");
        userService.singUp(userForm1);
        userService.singUp(userForm2);
    }
}

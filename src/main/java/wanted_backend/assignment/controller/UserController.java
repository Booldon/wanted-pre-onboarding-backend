package wanted_backend.assignment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wanted_backend.assignment.domain.User;
import wanted_backend.assignment.form.LoginForm;
import wanted_backend.assignment.form.UserForm;
import wanted_backend.assignment.service.UserService;

import java.awt.image.BufferedImage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/signUp")
    public String showSignUp(@ModelAttribute UserForm userForm) {
        return "/users/signUpForm";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid UserForm userForm, BindingResult result) {

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
}

package wanted_backend.assignment.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import wanted_backend.assignment.exception.LoginFailException;

@ControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(LoginFailException.class)
    public String handleLoginException(LoginFailException exception, Model model) {
        model.addAttribute("exception",exception);

        return "exception/LoginError";

    }
}

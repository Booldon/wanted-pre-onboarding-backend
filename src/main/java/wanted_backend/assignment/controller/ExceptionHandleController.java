package wanted_backend.assignment.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wanted_backend.assignment.exception.LoginFailException;
import wanted_backend.assignment.exception.NoAuthorization;

@RestControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(LoginFailException.class)
    public String handleLoginException() {
        return "로그인 에러 : 이메일 형식 또는 비밀번호 자릿수를 확인해주세요";

    }
    @ExceptionHandler(NoAuthorization.class)
    public String NoAuthorizationException() {


        return "해당 게시글에 대한 권한이 없습니다.";

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String EntityNotFoundException() {
        return "엔티티를 조회할 수 없습니다. 쿼리스트링을 확인해주세요.";
    }
}

package wanted_backend.assignment.service;

import wanted_backend.assignment.domain.Member;
import wanted_backend.assignment.form.LoginForm;
import wanted_backend.assignment.form.MemberForm;

public interface MemberServiceInterface {
    Member singUp(MemberForm newMemberForm);
    Member login(LoginForm loginForm);
    Member findByEmail(String email);

}

package wanted_backend.assignment.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted_backend.assignment.domain.Member;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Member save(Member user) {
        em.persist(user);
        return user;
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public Optional<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email =: email", Member.class)
                .setParameter("email",email)
                .getResultList().stream().findAny();
    }




}

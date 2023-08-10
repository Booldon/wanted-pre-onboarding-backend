package wanted_backend.assignment.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted_backend.assignment.domain.User;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public Optional<User> findByEmail(String email) {
        return em.createQuery("select u from User u where u.email =: email",User.class)
                .setParameter("email",email)
                .getResultList().stream().findAny();
    }




}

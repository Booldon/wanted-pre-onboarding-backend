package wanted_backend.assignment.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted_backend.assignment.domain.Post;

import javax.swing.text.Caret;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public List<Post> findByUserId(Long userId) {
        return em.createQuery("select p from Post p where p.user.id = :userId", Post.class)
                .setParameter("userId",userId)
                .getResultList();

    }

}

package wanted_backend.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted_backend.assignment.domain.Post;
import wanted_backend.assignment.form.PostForm;
import wanted_backend.assignment.dto.EditPostDto;
import wanted_backend.assignment.repository.PostRepository;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public Post create(PostForm createPostForm) {

        Post newPost = new Post();
        newPost.setTitle(createPostForm.getTitle());
        newPost.setContext(createPostForm.getContext());
        postRepository.save(newPost);

        return newPost;

    }

    public void update(Long postId, PostForm postForm){
        Post post = postRepository.getReferenceById(postId);
        post.setTitle(postForm.getTitle());
        post.setContext(postForm.getContext());
        postRepository.save(post);
    }


}

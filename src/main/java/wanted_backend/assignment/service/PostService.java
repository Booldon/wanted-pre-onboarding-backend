package wanted_backend.assignment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wanted_backend.assignment.domain.Post;
import wanted_backend.assignment.form.CreatePostForm;
import wanted_backend.assignment.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post create(CreatePostForm createPostForm) {

        Post newPost = new Post();
        newPost.setTitle(createPostForm.getTitle());
        newPost.setContext(createPostForm.getContext());
        postRepository.save(newPost);

        return newPost;

    }


}

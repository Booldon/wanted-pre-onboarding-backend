package wanted_backend.assignment.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wanted_backend.assignment.domain.Post;
import wanted_backend.assignment.form.CreatePostForm;
import wanted_backend.assignment.repository.PostRepository;
import wanted_backend.assignment.service.PostService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;


    @GetMapping("/new")
    public String createPost(@ModelAttribute CreatePostForm createPostForm) {
        return "post/createPostForm";
    }
    @PostMapping("/new")
    public String savePost(CreatePostForm createPostForm){
        postService.create(createPostForm);
        return "redirect:/post/postList";

    }
    @GetMapping("/list")
    public String postList(@RequestParam(value = "page", defaultValue = "0") Integer page, Model model) {
        PageRequest pageRequest = PageRequest.of(page,10);
        Page<Post> posts = postRepository.findAll(pageRequest);
        model.addAttribute("posts",posts);
        model.addAttribute("currentPage", page);
        return "post/postList";
    }

    @PostConstruct
    private void initializing() {
        for (int i = 0; i<100; i++) {
            Post post = new Post();
            post.setTitle("title"+i);
            post.setContext("context"+i);
            postRepository.save(post);
        }
    }
}

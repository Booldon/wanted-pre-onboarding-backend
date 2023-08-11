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
import wanted_backend.assignment.form.PostForm;
import wanted_backend.assignment.dto.EditPostDto;
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
    public String createPost(@ModelAttribute PostForm createPostForm) {
        return "post/createPostForm";
    }
    @PostMapping("/new")
    public String savePost(PostForm createPostForm){
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

    @GetMapping("/search")
    public String searchPost(@RequestParam("id") Long postId,
                             @RequestParam(value = "page",defaultValue = "0") Integer page,
                             Model model) {
        PageRequest pageRequest = PageRequest.of(0,10);
        Page<Post> posts = postRepository.findPageById(postId,pageRequest);
        model.addAttribute("posts",posts);
        model.addAttribute("currentPage", page);
        return "post/postList";
    }

    @GetMapping("/edit")
    public String editPostForm(@RequestParam("id") Long postId, Model model) {
        Post post = postRepository.getReferenceById(postId);

        EditPostDto editPostDto = new EditPostDto(post);

        model.addAttribute("post",post);
        return "post/editPostForm";
    }

    @PostMapping("/edit")
    public String editPost(@RequestParam("id") Long postId, @ModelAttribute PostForm postForm) {
        log.info("post is ="+postForm.getContext());
        postService.update(postId, postForm);
        return "redirect:/post/list";
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

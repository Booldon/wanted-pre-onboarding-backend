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
import wanted_backend.assignment.request.PostRequest;
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
    public String createPost(@ModelAttribute("post") PostRequest request) {
        return "post/createPostForm";
    }
    @PostMapping("/new")
    public String savePost(PostRequest request){
        postService.create(request);
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
    public String editPost(@RequestParam("id") Long postId, @ModelAttribute PostRequest request) {
        log.info("post is ="+request.getContext());
        postService.update(postId, request);
        return "redirect:/post/list";
    }
}
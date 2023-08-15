package wanted_backend.assignment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted_backend.assignment.domain.Post;
import wanted_backend.assignment.dto.PostDto;
import wanted_backend.assignment.repository.PostRepository;
import wanted_backend.assignment.service.PostService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;


    @PostMapping("/new")
    public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto){
        Post post = postService.create(postDto);
        PostDto newPostRequest = new PostDto().builder()
                .id(post.getId())
                .title(post.getTitle())
                .context(post.getContext())
                .member(post.getMember())
                .build();

        return new ResponseEntity<>(newPostRequest, HttpStatus.OK);
    }
    @GetMapping("/list")
    public Page<Post> postList(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        PageRequest pageRequest = PageRequest.of(page,3);
        Page<Post> posts = postRepository.findAll(pageRequest);
        return posts;
    }

    @GetMapping("/search")
    public Page<Post> searchPost(@RequestParam("id") Long postId,
                             @RequestParam(value = "page",defaultValue = "0") Integer page) {
        PageRequest pageRequest = PageRequest.of(0,3);
        Page<Post> posts = postRepository.findPageById(postId,pageRequest);
        return posts;
    }

    @PostMapping("/edit")
    public ResponseEntity<PostDto> editPost(@RequestParam("id") Long postId, @RequestBody PostDto postDto) {
        log.info("post is ={}",postDto.getContext());
        Post editPost = postService.update(postId, postDto);
        PostDto edPostRequest = new PostDto().builder()
                .id(editPost.getId())
                .title(editPost.getTitle())
                .context(editPost.getContext())
                .member(editPost.getMember())
                .build();

        return new ResponseEntity<>(edPostRequest,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public Page<Post> deletePost(@RequestParam("id") Long postId) {
        postService.delete(postId);
        log.info("delete id ={}",postId);
        PageRequest pageRequest = PageRequest.of(0,3);
        Page<Post> posts = postRepository.findAll(pageRequest);
        return posts;
    }
}
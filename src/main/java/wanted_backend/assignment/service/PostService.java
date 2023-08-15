package wanted_backend.assignment.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted_backend.assignment.domain.Member;
import wanted_backend.assignment.domain.Post;
import wanted_backend.assignment.jwt.SecurityUtil;
import wanted_backend.assignment.repository.MemberRepository;
import wanted_backend.assignment.request.PostRequest;
import wanted_backend.assignment.repository.PostRepository;

import java.net.http.HttpRequest;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    private final SecurityUtil securityUtil;
    public Post create(PostRequest createPostForm) {
        Member findMember = securityUtil.getMemberFromSecurityContext();
        Post newPost = new Post();
        newPost.setTitle(createPostForm.getTitle());
        newPost.setContext(createPostForm.getContext());
        newPost.setMember(findMember);
        postRepository.save(newPost);

        return newPost;

    }

    public void update(Long postId, PostRequest postForm){
        Post post = postRepository.getReferenceById(postId);
        Member findMember = securityUtil.getMemberFromSecurityContext();
        if(findMember.getId() == post.getId()) {
            post.setTitle(postForm.getTitle());
            post.setContext(postForm.getContext());
            postRepository.save(post);
        }

    }


}
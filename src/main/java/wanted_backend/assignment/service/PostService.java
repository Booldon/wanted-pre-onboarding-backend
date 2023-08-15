package wanted_backend.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted_backend.assignment.domain.Member;
import wanted_backend.assignment.domain.Post;
import wanted_backend.assignment.exception.NoAuthorization;
import wanted_backend.assignment.jwt.SecurityUtil;
import wanted_backend.assignment.repository.MemberRepository;
import wanted_backend.assignment.dto.PostDto;
import wanted_backend.assignment.repository.PostRepository;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    private final SecurityUtil securityUtil;
    public Post create(PostDto createPostForm) {
        Member findMember = securityUtil.getMemberFromSecurityContext();
        Post newPost = new Post();
        newPost.setTitle(createPostForm.getTitle());
        newPost.setContext(createPostForm.getContext());
        newPost.setMember(findMember);
        postRepository.save(newPost);

        return newPost;

    }

    public Post update(Long postId, PostDto postForm){
        Post post = postRepository.getReferenceById(postId);
        Member findMember = securityUtil.getMemberFromSecurityContext();
        if(findMember.getId() != post.getMember().getId()) {
            throw new NoAuthorization("작성자만 변경 가능합니다.");
        }
        post.setTitle(postForm.getTitle());
        post.setContext(postForm.getContext());
        postRepository.save(post);
        return post;
    }

    public void delete(Long postId){
        Post post = postRepository.getReferenceById(postId);
        Member findMember = securityUtil.getMemberFromSecurityContext();
        if(findMember.getId() != post.getMember().getId()) {
            throw new NoAuthorization("작성자만 변경 가능합니다.");
        }
        postRepository.delete(post);

    }


}
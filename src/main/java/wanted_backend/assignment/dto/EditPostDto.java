package wanted_backend.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import wanted_backend.assignment.domain.Member;
import wanted_backend.assignment.domain.Post;

@Getter
@Setter
public class EditPostDto {
    private Long id;
    private String title;
    private String context;

    private Member member;

    public EditPostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.context = post.getContext();
        this.member = post.getMember();

    }


}

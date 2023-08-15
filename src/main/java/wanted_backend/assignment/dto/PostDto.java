package wanted_backend.assignment.dto;

import lombok.*;
import wanted_backend.assignment.domain.Member;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String context;
    private Member member;
}

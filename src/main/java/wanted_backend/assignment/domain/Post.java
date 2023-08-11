package wanted_backend.assignment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Post {


    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    @Column(name ="post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String context;

}

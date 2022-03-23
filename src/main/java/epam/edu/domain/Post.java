package epam.edu.domain;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Post")
@Table(name = "post")
public class Post {

    @Id
    //сгенерировалась последовательность post_id_seq
    //для Postgress, Oracle рекомендуется GenerationType.SEQUENCE
    //MySQL не поддерживает SEQUENCE, для этой db только IDENTITY
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    //сгенерировалась вспомогательная таблица post_post_comments
    //post_id  bigint notnull foreign key (post_id->id);
    //comments_id bigint notnull foreign key (comments_id->id) unique comments_id;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PostComment> comments = new ArrayList<>();

    //сгенерировался дефолтный конструктор

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }
}

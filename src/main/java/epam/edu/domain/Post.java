package epam.edu.domain;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Post")
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue
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
}

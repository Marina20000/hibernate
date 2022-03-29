package epam.edu.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity(name = "PostComment")
@Table(name = "post_comment")
@Immutable//entity read-only
public class PostComment {

    @Id
    //сгенерировалась hibernate_seq. Она будет одна на все сущности,
    //в которых стоит GenerationType.AUTO
    //похоже, что от нее зависит и последовательность, которая сгенерировалась
    //в Post:  post_id_seq принимает значение на 1 больше, чем hibernate_seq
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //для PostComment IDENTITY и для Post  SEQUENCE  дает правильную генерацию id
    //во всех других случаях получалось, что последовательность организовывалась по факту одна.
    //Важно! IDENTITY отключает пакетное обновление!!!!!!!!!!
    private Long id;
    private String review;
    //сгенерировался дефолтный конструктор
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public void setId(Long id) {
        this.id = id;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

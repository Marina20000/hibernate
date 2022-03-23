package epam.edu.domain;

import javax.persistence.*;

@Entity(name = "PostComment")
@Table(name = "post_comment")
public class PostComment {

    @Id
    //сгенерировалась hibernate_seq. Она будет одна на все сущности,
    //в которых стоит GenerationType.AUTO
    //похоже, что от нее зависит и последовательность, которая сгенерировалась
    //в Post:  post_id_seq принимает значение на 1 больше, чем hibernate_seq
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String review;
    //сгенерировался дефолтный конструктор

    public void setId(Long id) {
        this.id = id;
    }

    public void setReview(String review) {
        this.review = review;
    }
}

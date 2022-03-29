package epam.edu.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Post")
@Table(name = "post")
@DynamicUpdate //будет генерировать запрос на обновление ТОЛЬКО тех полей, которые поменялись
//проверка полей, которые поменялись, сопровождается довольно большими накладными расходами
//поэтому будет работать гораздо медленнее. Оправдано только там, где есть много полей
public class Post {

    @Id
    //сгенерировалась последовательность post_id_seq
    //для Postgress, Oracle рекомендуется GenerationType.SEQUENCE
    //MySQL не поддерживает SEQUENCE, для этой db только IDENTITY
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Basic( fetch = FetchType.LAZY )
    private String title;
    //сгенерировалась вспомогательная таблица post_post_comments
    //post_id  bigint notnull foreign key (post_id->id);
    //comments_id bigint notnull foreign key (comments_id->id) unique comments_id;
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    //CascadeType.ALL Каскад.ALL распространяет все операции, включая операции,
    // связанные с гибернацией, от родительского объекта к дочернему.
    //Есть еще :     ALL,PERSIST,MERGE,REMOVE,REFRESH,DETACH;
    //@JoinColumn(name = "post_id") //убираем таблицу post_post_comments
    //если не указывать по какому столбцу джойнить, то появится вспомогательная таблица post_post_comments
    //если есть @JoinColumn, то не надо mappedBy = "post",
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

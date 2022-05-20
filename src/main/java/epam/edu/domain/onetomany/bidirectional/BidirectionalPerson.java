package epam.edu.domain.onetomany.bidirectional;

import epam.edu.domain.onetomany.unidirectional.Phone;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Entity
@Table(name = "bidirectionalperson")
public class BidirectionalPerson {
    @Id
    @GeneratedValue
    private Long id;
    //Выводы: без дополнительной синхронизации в Phones все равно не тянутся BidirectionalPerson  (т.е. они всегда NULL)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "bidirectionalPerson")
    //mappedBy = "bidirectionalPerson" - аналогично действию  @JoinColumn(name = "person_id") - не
    //дает сгенерироваться вспомогательной таблице
    //Указывает, что внешний ключ ForeignKey  будет находиться в таблице bidirectionalPhones
    //и являться bidirectionalperson_id
    private List<BidirectionalPhone> bidirectionalPhones = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public List<BidirectionalPhone> getPhones() {
        return bidirectionalPhones;
    }

    public void addPhone(BidirectionalPhone phone){
        phone.setBidirectionalPerson(this);
        this.getPhones().add(phone);
    }

    @Override
    public String toString() {
        return "BidirectionalPerson{" +
                "id=" + id +
                ", bidirectionalPhones=" + bidirectionalPhones +
                '}';
    }
}
// Отношение bidirectional. Но надо дополнительно синхронизировать id обеих сущностей:
//При этом в конфигурации возникает в phone  поле person_id, которое по умолчанию ничем не заполняется (там null)
//и его надо заполнять вручную:
//public void addComment(PostComment comment) {
//    comments.add(comment);
//    comment.setPost(this);
//}
//    public void removeComment(PostComment comment) {
//        comments.remove(comment);
//        comment.setPost(null);
//    }


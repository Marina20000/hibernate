package epam.edu.domain.onetomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Person")
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "person_id")
    private List<Phone> phones = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public List<Phone> getPhones() {
        return phones;
    }
}
//Вариант 1  В таком варианте возникает вспомогательная таблица person_phone, составленная из пар
// (person_id, phone_id). Операции добавления и удаления корректны.
//Отношение unidirectional, владелец - всегда тот, у которого @OneToMany
//
//@Entity(name = "Person")
//public class Person {
//    @Id
//    @GeneratedValue
//    private Long id;
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Phone> phones = new ArrayList<>();
//
//@Entity(name = "Phone")
//public class Phone {
//    @Id
//    @GeneratedValue
//    private Long id;
//    @Column(name = "`number`")
//    private String number;

//Вариант 2  Отношение bidirectional. Но надо дополнительно синхронизировать id обеих сущностей:
//При это1 конфигурации возникает в phone  поле person_id, которое по умолчанию ничем не заполняется (там null)
//и его надо заполнять вручную:
//public void addComment(PostComment comment) {
//    comments.add(comment);
//    comment.setPost(this);
//}
//    public void removeComment(PostComment comment) {
//        comments.remove(comment);
//        comment.setPost(null);
//    }
//@Entity(name = "Person")
//public class Person {
//    @Id
//    @GeneratedValue
//    private Long id;
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)//сюда можно добавить mappedBy = "person" - ничего не меняется
//    private List<Phone> phones = new ArrayList<>();
//
//@Entity(name = "Phone")
//public class Phone {
//    @Id
//    @GeneratedValue
//    private Long id;
//    @Column(name = "`number`")
//    private String number;
//    @ManyToOne
//    private Person person;


//            <plugin>
//                <groupId>org.hibernate.orm.tooling</groupId>
//                <artifactId>hibernate-enhance-maven-plugin</artifactId>
//                <version>5.4.2.Final</version>
//                <executions>
//                    <execution>
//                        <configuration>
//                            <failOnError>true</failOnError>
//                            <enableLazyInitialization>true</enableLazyInitialization>
//                            <enableDirtyTracking>true</enableDirtyTracking>
//                            <enableAssociationManagement>true</enableAssociationManagement>
//                        </configuration>
//                        <goals>
//                            <goal>enhance</goal>
//                        </goals>
//                    </execution>
//                </executions>
//            </plugin>

//        у меня не заработал enchance кода:
//        Person person = new Person();
//        person.setName( "John Doe" );
//        Book book = new Book();
//        person.getBooks().add( book );
//        try {
//            book.getAuthor().getName(); - у меня не заработала перекрестная ссылка.
//          получаю NPE
//        }
//        hibernateProperties.put("hibernate.enhancer.enableDirtyTracking", "true");
//        hibernateProperties.put("hibernate.enhancer.enableLazyInitialization", "true");
//        hibernateProperties.put("hibernate.enhancer.enableAssociationManagement", "true");
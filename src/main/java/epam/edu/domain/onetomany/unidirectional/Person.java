package epam.edu.domain.onetomany.unidirectional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Person")
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "person_id")//без @JoinColumn возникает вспомогательная таблица person_phone, составленная из пар (person_id, phone_id)
    private List<Phone> phones = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", phones=" + phones +
                '}';
    }
}
//Вариант 1. Отношение unidirectional, владелец - всегда тот, у которого @OneToMany
// (Если не будет @JoinColumn, то в таком варианте возникает вспомогательная таблица person_phone, составленная из пар
// (person_id, phone_id). Операции добавления и удаления корректны.


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
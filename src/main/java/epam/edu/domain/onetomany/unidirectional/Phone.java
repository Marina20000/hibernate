package epam.edu.domain.onetomany.unidirectional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Phone")
public class Phone {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "`number`")
    private String number;

    public Phone() {
    }

    public Phone(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}

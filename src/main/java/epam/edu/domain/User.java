package epam.edu.domain;

import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Setter
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
}

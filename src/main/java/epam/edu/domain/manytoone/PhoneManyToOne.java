package epam.edu.domain.manytoone;

import javax.persistence.*;

@Entity
public class PhoneManyToOne {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "`number`")
    private String number;

    //В таблице Phone создается поле person_id, которое является foreign_key для person_id
    @ManyToOne
    @JoinColumn(name = "personmanytoone_id",
            foreignKey = @ForeignKey(name = "PERSONMANYTOONE_ID_FK")
    )
    private PersonManyToOne person;
}

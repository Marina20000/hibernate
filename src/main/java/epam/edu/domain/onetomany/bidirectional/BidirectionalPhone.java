package epam.edu.domain.onetomany.bidirectional;

import javax.persistence.*;

@Entity
@Table(name="bidirectionalphone")
public class BidirectionalPhone {
    @Id
    @GeneratedValue
    private Long id;


    @Column(name = "`number`")
    private String number;

    @ManyToOne
    private BidirectionalPerson bidirectionalPerson;



    public BidirectionalPhone() {
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setBidirectionalPerson(BidirectionalPerson bidirectionalPerson) {
        this.bidirectionalPerson = bidirectionalPerson;
    }

    public BidirectionalPhone(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "BidirectionalPhone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", bidirectionalPerson=" + bidirectionalPerson +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public BidirectionalPerson getBidirectionalPerson() {
        return bidirectionalPerson;
    }
}

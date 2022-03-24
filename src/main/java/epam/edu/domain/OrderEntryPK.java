package epam.edu.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderEntryPK implements Serializable {
    private long orderId;
    private long productId;
}

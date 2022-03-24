package epam.edu.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class OrderEntry {
    @EmbeddedId
    private OrderEntryPK entryId;
}
//составной ключ можно также определить как:
//@Entity
//@IdClass(OrderEntryPK.class)
//public class OrderEntry {
//    @Id
//    private long orderId;
//    @Id
//    private long productId;
//
//    // ...
//}
//Обратите внимание, что для обоих типов составных идентификаторов класс первичного ключа
// также может содержать атрибуты @ManyToOne .
//Hibernate также позволяет определять первичные ключи, состоящие из @ManyToOne ассоциаций
// в сочетании с @Id аннотацией. В этом случае класс сущности также должен выполнять условия
// класса первичного ключа.
//Недостатком этого метода является отсутствие разделения между объектом сущности и идентификатором.

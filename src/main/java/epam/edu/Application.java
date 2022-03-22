package epam.edu;

import epam.edu.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

public class Application {
    public static void main(String... args) {
        ConfigurableApplicationContext context =
                new AnnotationConfigApplicationContext("epam.edu.configuration");
        LocalSessionFactoryBean sessionFactoryBean = context.getBean(LocalSessionFactoryBean.class);
        SessionFactory sessionFactory = sessionFactoryBean.getObject();
        assert sessionFactory != null;
        try (Session session = sessionFactory.openSession()) {
            User user = new User();
            user.setName("a");
            //генерирует и добавляет Id Hibernate: call next value for hibernate_sequence
            //также добавляет в Map<EntityKey, Object>((SessionImpl) session).persistenceContext.entitiesByKey
            //новую сущность. Основной класс - StatefulPersistentContext
            session.persist(user);
            //Hibernate: insert into User (name, id) values (?, ?)
            session.save(user);
            session.beginTransaction();
            session.flush();//flush упал без транзакции
            session.getTransaction().commit();
            session.detach(user);//убрал id user из Map
            //при закрытии session очищаются все Map, хранившие объекты, и
            //объекты, которые открывались для автогенерации
            //транзакция становится invalidate
        }
    }
}

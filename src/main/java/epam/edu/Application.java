package epam.edu;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

        }
    }
}

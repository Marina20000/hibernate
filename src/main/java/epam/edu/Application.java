package epam.edu;

import epam.edu.domain.Post;
import epam.edu.domain.PostComment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.Arrays;

public class Application {
    public static void main(String... args) {
        ConfigurableApplicationContext context =
                new AnnotationConfigApplicationContext("epam.edu.configuration");
        LocalSessionFactoryBean sessionFactoryBean = context.getBean(LocalSessionFactoryBean.class);
        SessionFactory sessionFactory = sessionFactoryBean.getObject();
        assert sessionFactory != null;
        try (Session session = sessionFactory.openSession()) {
            PostComment postComment = new PostComment();
            postComment.setReview("review");
            Post post = new Post();
            post.setTitle("title");
            post.setComments(Arrays.asList(postComment));
            session.beginTransaction();
            session.persist(postComment);
            session.save(post);
            session.getTransaction().commit();
        }
    }
}

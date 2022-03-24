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
            session.beginTransaction();
            //postComment записывается сам, т.к. стоит CASCADE.ALL
            //session.persist(postComment);
            for(int i=0; i<10; i++) {
                PostComment postComment = new PostComment();
                postComment.setReview("review");
                Post post = new Post();
                post.setTitle("title");
                post.setComments(Arrays.asList(postComment));
                session.save(post);
            }
            //в лог вывелось
            //Before Query Execution
            //INFO  CommonsQueryLoggingListener -
            //Name:, Time:3, Success:True
            //Type:Prepared, Batch:True, QuerySize:1, BatchSize:5
            //Query:["insert into post_post_comment (Post_id, comments_id) values (?, ?)"]
            //Params:[(3,3),(4,4),(5,5),(6,6),(7,7)]      началось с 3, т.к. уже были записи.
            //Объем батча равен 5. Потом записалась еще вторая порция
            session.getTransaction().commit();
        }
    }
}

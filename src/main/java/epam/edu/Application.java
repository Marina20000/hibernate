package epam.edu;

import epam.edu.domain.onetomany.bidirectional.BidirectionalPerson;
import epam.edu.domain.onetomany.bidirectional.BidirectionalPhone;
import epam.edu.domain.onetomany.unidirectional.Person;
import epam.edu.domain.onetomany.unidirectional.Phone;
import epam.edu.queries.HqlExample;
import net.sf.ehcache.CacheManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;


public class Application {
    public static void main(String... args) {
        ConfigurableApplicationContext context =
                new AnnotationConfigApplicationContext("epam.edu.configuration", "epam.edu.queries");
        LocalSessionFactoryBean sessionFactoryBean = context.getBean(LocalSessionFactoryBean.class);
        SessionFactory sessionFactory = sessionFactoryBean.getObject();
        assert sessionFactory != null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            //postComment записывается сам, т.к. стоит CASCADE.ALL
            //session.persist(postComment);
//            for(int i=0; i<10; i++) {
//                PostComment postComment = new PostComment();
//                postComment.setReview("review");
//                Post post = new Post();
//                post.setTitle("title");
//                post.setComments(Arrays.asList(postComment));
//                session.save(post);
//            }
            //в лог вывелось
            //Before Query Execution
            //INFO  CommonsQueryLoggingListener -
            //Name:, Time:3, Success:True
            //Type:Prepared, Batch:True, QuerySize:1, BatchSize:5
            //Query:["insert into post_post_comment (Post_id, comments_id) values (?, ?)"]
            //Params:[(3,3),(4,4),(5,5),(6,6),(7,7)]      началось с 3, т.к. уже были записи.
            //Объем батча равен 5. Потом записалась еще вторая порция


            Person person = new Person();
            Phone phone1 = new Phone("123-456-7890");
            Phone phone2 = new Phone("321-654-0987");

            person.getPhones().add(phone1);//при такой конфигурации- не загрузились id в phone_person_id
            person.getPhones().add(phone2);
            session.persist(person);
            session.flush();
            Long id = person.getId();
            //session.delete(phone1); - так просто удалять нельзя! надо: person.getPhones().remove(phone1)
            //иначе вообще ничего не удалится!
            //манипуляции с удалением дочерних сущностей - только через родительскую!!!!!
            //System.out.println(person.getPhones().size());
            session.clear();

            BidirectionalPerson bidirectionalPerson = new BidirectionalPerson();
            BidirectionalPhone bidirectionalPhone1 = new BidirectionalPhone("111");
            BidirectionalPhone bidirectionalPhone2 = new BidirectionalPhone("222");
            bidirectionalPerson.addPhone(bidirectionalPhone1);
            bidirectionalPerson.addPhone(bidirectionalPhone2);
            session.persist(bidirectionalPerson);
            id = bidirectionalPerson.getId();
            Long phoneId = bidirectionalPhone1.getId();
            session.getTransaction().commit();
            BidirectionalPerson loadedPerson = session.get(BidirectionalPerson.class, id);
            HqlExample hqlExample = context.getBean(HqlExample.class);
            hqlExample.hqlQuery(session);
            hqlExample.criteriaExample(session);
            int size = CacheManager.ALL_CACHE_MANAGERS.get(0)
                    .getCache("com.baeldung.hibernate.cache.model.Foo").getSize();
            session.close();
            //System.out.println(loadedPerson);
        }
    }
}

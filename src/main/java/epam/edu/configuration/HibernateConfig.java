package epam.edu.configuration;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
                "epam.edu.domain");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        //https://easyjava.ru/data/pool/nastrojka-dbcp/
//        DBCP, как это ни удивительно звучит, ещё одна библиотека для создания пулов соединений. Вместе с HikariCP и c3p0 они составляют триумвират наиболее популярных библиотек пулов для java. DBCP разрабатывается The Apache Foundation, что сделало его некоторым образом тяжёловесным.
//        Простое создание пула соединений:
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://127.0.0.1/test");
        dataSource.setUsername("tester");
        dataSource.setPassword("q1w2e3r4");

        //Сконфигурировать connection pool http://www.mastertheboss.com/hibernate-jpa/hibernate-configuration/configure-a-connection-pool-with-hibernate/
        // Minimum number of ideal connections in the pool
        dataSource.setMinIdle(0);
        // Maximum number of ideal connections in the pool
        dataSource.setMaxIdle(5);
        // Maximum number of actual connection in the pool
        dataSource.setMaxTotal(20);

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        //Опция  <property name="hibernate.hbm2ddl.auto">update</property>
        // говорит Hibernate, что надо сканировать все классы, имеющие аннотацию @Entity и
        // обновить схему таблиц базы данных сообразно этим классам
        //Hibernate: drop table User if exists
        //Hibernate: drop sequence if exists hibernate_sequence
        //Hibernate: create sequence hibernate_sequence start with 1 increment by 1
        //Hibernate: create table User (id integer not null, name varchar(255), primary key (id))
        //hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop"); - пришлось закомментировать,
        //т.к. в первый раз создались таблицы и последовательности, а потом не все удалялось, и
        //при следующих запусках с любыми параметрами были ошибки, что уже что-нибудь существует.
//        validate: проверить схему, не вносить изменения в базу данных.
//        update: обновить схему.
//        create: создает схему, уничтожая предыдущие данные.
//        create-drop: отказаться от схемы, когда SessionFactory закрывается явно, как правило, когда приложение остановлено.
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
       // hibernateProperties.setProperty("hibernate.format_sql", "true");
        hibernateProperties.setProperty("hibernate.generate_statistics", "true");
        return hibernateProperties;
    }
}

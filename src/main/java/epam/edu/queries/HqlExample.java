package epam.edu.queries;

import epam.edu.domain.onetomany.unidirectional.Phone;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HqlExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(HqlExample.class);

    public void hqlQuery(Session session) {
        String hql = "FROM Phone";
        Query query = session.createQuery(hql);
        query.setFirstResult(1);
        query.setMaxResults(10);
        query.setCacheable(true);
        query.setCacheRegion("phone");
        List results = query.list();
        LOGGER.info(results.toString());
    }

    public void criteriaExample(Session session) {
        Criteria cr = session.createCriteria(Phone.class);

// To get total row count.
        cr.setProjection(Projections.rowCount());

// To get average of a property.
        cr.setProjection(Projections.avg("id"));

// To get distinct count of a property.
        cr.setProjection(Projections.countDistinct("number"));

// To get maximum of a property.
        cr.setProjection(Projections.max("id"));

// To get minimum of a property.
        cr.setProjection(Projections.min("id"));

// To get sum of a property.
        cr.setProjection(Projections.sum("id"));

        List result = cr.list();

        LOGGER.info(result.toString());
    }

    public void nativeQuery(Session session){
        String sql = "SELECT * FROM PHONE";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Phone.class);
        List results = query.list();
        LOGGER.info(results.toString());
    }
}

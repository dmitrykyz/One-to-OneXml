package by.academy.it.db;

import by.academy.it.db.exceptions.DaoException;
import by.academy.it.loader.StartLoader;
import by.academy.it.pojos.EmployeeDetail;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Dmitry on 11/10/2016.
 */
public class EmployeeDetailDAO extends BaseDao<EmployeeDetail> {

    private static Logger log = Logger.getLogger(EmployeeDetailDAO .class);
    private Transaction transaction = null;

    public void deleteEmployeeDetailByCity(String city) throws DaoException {
        log.info("Delete EmployeeDetail by City:" +  city);
        try {

            Session session = StartLoader.util.getSession();
            transaction = session.beginTransaction();
            String hql = "DELETE FROM EmployeeDetail ED WHERE ED.city=:cityParam";
            Query query = session.createQuery(hql);
            query.setParameter("cityParam", city );
            Integer result = query.executeUpdate();
            log.info("Delete EmployeeDetail by City cuscced  :" + result);
            transaction.commit();
        } catch (HibernateException e) {
            log.error("Error Delete EmployeeDetail by City  :" + city + " in Dao" + e);
            transaction.rollback();
            throw new DaoException(e);
        }
    }
}

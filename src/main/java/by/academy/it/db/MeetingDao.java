package by.academy.it.db;

import by.academy.it.db.exceptions.DaoException;
import by.academy.it.loader.StartLoader;
import by.academy.it.pojos.Meeting;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Dmitry on 11/11/2016.
 */
public class MeetingDao extends BaseDao<Meeting>{

    private static Logger log = Logger.getLogger(MeetingDao.class);
    private Transaction transaction = null;

    public List<Meeting> loadListMeetingBySubject(String subject) throws DaoException {
        log.info("Load List<Meeting> by  subject:" +  subject);
        List<Meeting> meetings = null;
        try {

            Session session = StartLoader.util.getSession();
            transaction = session.beginTransaction();
            String hql = "SELECT M FROM Meeting M WHERE M.subject like:subjectParam";
            Query query = session.createQuery(hql);
            query.setParameter("subjectParam", subject + "%" );
            query.setFirstResult(0);
            query.setMaxResults(3);
            meetings = query.list();
            log.info("load() List<Meeting> by :" + subject);
            transaction.commit();
        } catch (HibernateException e) {
            log.error("Error load() List<Meeting> by :" + subject + " in Dao" + e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return meetings;
    }

}

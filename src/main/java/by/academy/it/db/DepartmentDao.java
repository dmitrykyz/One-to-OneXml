package by.academy.it.db;

import by.academy.it.db.exceptions.DaoException;
import by.academy.it.pojos.Department;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import static by.academy.it.loader.StartLoader.util;

/**
 * Created by Dmitry on 11/7/2016.
 */
public class DepartmentDao extends BaseDao<Department> {

    private static Logger log = Logger.getLogger(DepartmentDao.class);

    public void flush(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            setTransaction(session.beginTransaction());
            Department department = (Department)session.get(Department.class, id);
            System.out.println(department);
            System.out.println("isDirty = " + session.isDirty());
            department.setDepartName(newName);
            System.out.println(department);
            System.out.println("isDirty = " + session.isDirty());
            session.flush();
            System.out.println(department);
            System.out.println("isDirty = " + session.isDirty());
            getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Error Flush Department" + e);
            throw new DaoException(e);
        }

    }

    public void refresh(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            Department department = (Department)session.get(Department.class, id);
            System.out.println(department);
            System.out.println("isDirty = " + session.isDirty());
            department.setDepartName(newName);
            System.out.println(department);
            System.out.println("isDirty = " + session.isDirty());
            session.refresh(department);
            System.out.println(department);
            System.out.println("isDirty = " + session.isDirty());
        } catch (HibernateException e) {
            log.error("Error Refresh Department" + e);
            throw new DaoException(e);
        }

    }

}

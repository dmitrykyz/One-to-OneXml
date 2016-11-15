package by.academy.it.db;

import by.academy.it.db.exceptions.DaoException;
import by.academy.it.loader.StartLoader;
import by.academy.it.pojos.Department;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static by.academy.it.loader.StartLoader.util;

/**
 * Created by Dmitry on 11/7/2016.
 */
public class DepartmentDao extends BaseDao<Department> {

    private static Logger log = Logger.getLogger(DepartmentDao.class);
    private Transaction transaction = null;

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

    public List<Department> loadListDepartmentByDepartmentName(String departmentName) throws DaoException {
        log.info("Load List<Department> by departmentName:" + departmentName);
        List<Department> departments = null;
        try {

            Session session = StartLoader.util.getSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Department.class);
            criteria.add(Restrictions.like("departName","%" + departmentName + "%"));
            criteria.addOrder(Order.asc("departName"));
            criteria.setFirstResult(0);
            criteria.setMaxResults(3);
            departments = criteria.list();
            log.info("load() List<Department>  by :" + departmentName);
            transaction.commit();
        } catch (HibernateException e) {
            log.error("Error load() List<Department> by :" + departmentName + " in Dao" + e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return departments;
    }

}

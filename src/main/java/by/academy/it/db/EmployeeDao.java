/*
 * Copyright (c) 2012 by VeriFone, Inc.
 * All Rights Reserved.
 *
 * THIS FILE CONTAINS PROPRIETARY AND CONFIDENTIAL INFORMATION
 * AND REMAINS THE UNPUBLISHED PROPERTY OF VERIFONE, INC.
 *
 * Use, disclosure, or reproduction is prohibited
 * without prior written approval from VeriFone, Inc.
 */
package by.academy.it.db;

import by.academy.it.db.exceptions.DaoException;
import by.academy.it.loader.StartLoader;
import by.academy.it.pojos.Employee;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static by.academy.it.loader.StartLoader.util;


public class EmployeeDao extends BaseDao<Employee> {

    private static Logger log = Logger.getLogger(EmployeeDao.class);
    private Transaction transaction = null;

    public void flush(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            setTransaction(session.beginTransaction());
            Employee employee = (Employee)session.get(Employee.class, id);
            System.out.println(employee);
            System.out.println("isDirty = " + session.isDirty());
            employee.setFirstName(newName);
            System.out.println(employee);
            System.out.println("isDirty = " + session.isDirty());
            session.flush();
            System.out.println(employee);
            System.out.println("isDirty = " + session.isDirty());
            getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Error Flush person" + e);
            throw new DaoException(e);
        }

    }

    public void refresh(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            Employee employee = (Employee)session.get(Employee.class, id);
            System.out.println(employee);
            System.out.println("isDirty = " + session.isDirty());
            employee.setFirstName(newName);
            System.out.println(employee);
            System.out.println("isDirty = " + session.isDirty());
            session.refresh(employee);
            System.out.println(employee);
            System.out.println("isDirty = " + session.isDirty());
        } catch (HibernateException e) {
            log.error("Error Refresh employee" + e);
            throw new DaoException(e);
        }

    }

    public List<Employee> loadListEmployeeByFirstName(String firstName) throws DaoException {
        log.info("Load List<Employee> by firstName:" + firstName);
        List<Employee> employees = null;
        try {

            Session session = StartLoader.util.getSession();
            transaction = session.beginTransaction();
            String hql = "SELECT E FROM Employee E WHERE E.firstName=:firstNameParam ORDER BY E.employeeid DESC";
            Query query = session.createQuery(hql);
            query.setParameter("firstNameParam", firstName);
            employees = query.list();
            System.out.println("isDirty before commit = "+ session.isDirty());
            log.info("load() List<Employee> by :" + firstName);
            transaction.commit();
        } catch (HibernateException e) {
            log.error("Error load() List<Employee> by :" + firstName + " in Dao" + e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return employees;
    }

}

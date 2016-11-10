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
import by.academy.it.pojos.Employee;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import static by.academy.it.loader.StartLoader.util;

/**
 * User: yslabko
 * Date: 14.04.14
 * Time: 13:05
 */
public class EmployeeDao extends BaseDao<Employee> {

    private static Logger log = Logger.getLogger(EmployeeDao.class);

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

}

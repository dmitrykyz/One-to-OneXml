<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping package="by.academy.it.pojos" auto-import="false" catalog="testdbmanytomany" default-access="property">
    <class name="Employee" table="T_EMPLOYEE" lazy="false">
        <id name="employeeid" type="int">
            <generator class="increment"/>
        </id>

        <one-to-one name="employeeDetail" class="EmployeeDetail"></one-to-one>

        <many-to-one name="department" class="by.academy.it.pojos.Department" fetch="select" column="F_DEPARTMENT_ID" />

        <set name="meetings" table="T_EMPLOYEE_MEETING"
             inverse="false" lazy="true" fetch="select" cascade="all">
            <key column="F_EMPLOYEE_ID"/>
            <many-to-many column="F_MEETING_ID" class="Meeting"/>
        </set>

        <property name="firstName" type="string"/>
        <property name="lastName" type="string"/>
        <property name="cellphone" type="string"/>

    </class>
</hibernate-mapping>



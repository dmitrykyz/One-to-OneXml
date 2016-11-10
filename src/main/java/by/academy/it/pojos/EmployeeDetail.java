package by.academy.it.pojos;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by Dmitry on 11/4/2016.
 */
public class EmployeeDetail implements Serializable {
    private static final long serialVersinUID = 3L;

    private Integer employeeId;
    private String street;
    private String city;
    private String state;
    private String country;
    private Employee employee;

    public EmployeeDetail() {
    }

    public static long getSerialVersinUID() {
        return serialVersinUID;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EmployeeDetail that = (EmployeeDetail) o;

        return new EqualsBuilder()
                .append(street, that.street)
                .append(city, that.city)
                .append(state, that.state)
                .append(country, that.country)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(street)
                .append(city)
                .append(state)
                .append(country)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "EmployeeDetail{" +
                "employeeId=" + employeeId +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

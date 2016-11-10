package by.academy.it.loader;

import by.academy.it.db.DepartmentDao;
import by.academy.it.db.EmployeeDao;
import by.academy.it.db.EmployeeDetailDAO;
import by.academy.it.db.exceptions.DaoException;
import by.academy.it.pojos.Department;
import by.academy.it.pojos.Employee;
import by.academy.it.pojos.EmployeeDetail;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Dmitry on 11/9/2016.
 */
public class MenuLoadeManyToMany {
    private static Logger log = Logger.getLogger(MenuLoadeManyToMany.class);
    public static Boolean needMenu = true;
    private static EmployeeDao employeeDao = null;
    private static EmployeeDetailDAO employeeDetailDAO = null;
    private static DepartmentDao departmentDAO = null;

    public static void menu() throws DaoException {
        Employee employee = null;
        EmployeeDetail employeeDetail = null;
        Department department  = null;
        while (needMenu) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    System.exit(0); break;
                case 1:
                    delete();
                    break;
                case 2:
                    employee = findEmployee();
                    System.out.println(employee.getEmployeeDetail());
                    System.out.println(employee.getDepartment());
                    break;
                case 3:
                    employeeDetail = findEmployeeDetail();
                    System.out.println(employeeDetail.getEmployee());
                    System.out.println(employeeDetail.getEmployee().getDepartment());
                    break;
                case 4:
                    department = findDepartment();
                    Set<Employee> employeesTemp = department.getEmployees();
                    for (Employee employee1: employeesTemp) {
                        System.out.println("Employee" + employee1);
                        System.out.println("EmployeeDetail" + employee1.getEmployeeDetail());
                    }
                    break;
                case 5:
                    Department department1 = createDepartment();
                    EmployeeDetail employeeDetail1 = createEmployeeDetail(employeeDetail);
                    employee = createEmployee(employeeDetail1);

                    getDepartmentDao().saveOrUpdate(department1);

                    employee.setEmployeeDetail(employeeDetail);

                    employee.setDepartment(department1);
                    Set<Employee> employees = new HashSet<Employee>();
                    employees.add(employee);
                    department1.setEmployees(employees);

                    employeeDetail1.setEmployee(employee);

                    getEmployeeDao().saveOrUpdate(employee);
                    getEmployeeDetailDAO().saveOrUpdate(employeeDetail1);
                    break;
                case 6:
                    //employee = loadEmployee();
                    break;
                case 7:
                    //flushSession();
                    break;
                case 8:
                    //refreshSession();
                    break;
                default:
                    needMenu = true;
                    break;
            }
            needMenu = true;
        }

    }

    private static void printMenu() {
        System.out.println(" Options:");
        System.out.println("        0. Exit");
        System.out.println("        1. Delete Employee");
        System.out.println("        2. Get All by Employee ID");
        System.out.println("        3. Get All by EmployeeDetail ID");
        System.out.println("        4. Get All by Department ID");
        System.out.println("        5. Save or Update Department Employee Employee Detail");
      //  System.out.println("        5. Load Employee");
      //  System.out.println("        6. Flush Session");
      //  System.out.println("        6. Refresh Session");
    }

    private static Employee createEmployee(EmployeeDetail employeeDetail) {
        System.out.println("Please enter Employee description:");
        System.out.print("First Name - ");

        Employee emploeer = new Employee();
        Scanner scanner = new Scanner(System.in);
        String parameter = scanner.nextLine();
        emploeer.setFirstName(parameter);
        System.out.print("Last Name - ");
        parameter = scanner.nextLine();
        emploeer.setLastName(parameter);
        System.out.print("Cellphone - ");
        parameter = scanner.nextLine();
        emploeer.setCellphone(parameter);

        return emploeer;
    }

    private static EmployeeDetail createEmployeeDetail(EmployeeDetail employeeDetail){
        System.out.println("Please enter Employee Detail description:");

        Scanner scanner = new Scanner(System.in);
        employeeDetail = new EmployeeDetail();

        System.out.print("Street - ");
        String parameter = scanner.nextLine();
        employeeDetail.setStreet(parameter);
        System.out.print("City - ");
        parameter = scanner.nextLine();
        employeeDetail.setCity(parameter);
        System.out.print("State - ");
        parameter = scanner.nextLine();
        employeeDetail.setState(parameter);
        System.out.print("Country - ");
        parameter = scanner.nextLine();
        employeeDetail.setCountry(parameter);

        return employeeDetail;
    }

    private static Department createDepartment() {
        System.out.println("Please enter Department description:");
        System.out.print("Department Name - ");

        Department department = new Department();
        Scanner scanner = new Scanner(System.in);
        String parameter = scanner.nextLine();
        department.setDepartName(parameter);
        return department;
    }

    public static Employee findEmployee() {
        System.out.println("Get by Id. Please enter Employee id:");
        System.out.print("Id - ");

        Scanner scanner = new Scanner(System.in);
        Employee employee = null;
        Integer id = scanner.nextInt();
        try {
            employee = getEmployeeDao().get(id);
        } catch (DaoException e) {
            log.error(e, e);
        } catch (NullPointerException e) {
            log.error("Unable find employee:", e);
        }
        System.out.print(employee);
        return employee;
    }

    public static EmployeeDetail findEmployeeDetail() {
        System.out.println("Get by Id. Please enter Employee Detail id:");
        System.out.print("Id - ");

        Scanner scanner = new Scanner(System.in);
        EmployeeDetail employeeDetail = null;
        Integer id = scanner.nextInt();
        try {
            employeeDetail = getEmployeeDetailDAO().get(id);
        } catch (DaoException e) {
            log.error(e, e);
        } catch (NullPointerException e) {
            log.error("Unable find employeeDetail:", e);
        }
        System.out.print(employeeDetail);
        return employeeDetail;
    }

    public static Department findDepartment() {
        System.out.println("Get by Id. Please enter Department id:");
        System.out.print("Id - ");

        Scanner scanner = new Scanner(System.in);
        Department department = null;
        Integer id = scanner.nextInt();
        try {
            department = getDepartmentDao().get(id);
        } catch (DaoException e) {
            log.error(e, e);
        } catch (NullPointerException e) {
            log.error("Unable find employeeDetail:", e);
        }
        System.out.print(department);
        return department;
    }

    private static void delete() {
        System.out.println("Delete by Id. Please enter Department id:");
        System.out.print("Id - ");

        Scanner scanner = new Scanner(System.in);
        Department department = null;
        Set<Employee> employees = null;
        Employee employee = null;
        EmployeeDetail employeeDetail = null;
        Integer id = scanner.nextInt();
        try {
            department = getDepartmentDao().get(id);
            employees = department.getEmployees();
            for (Employee employee1: employees) {
                employee = getEmployeeDao().get(id);
                employeeDetail = getEmployeeDetailDAO().get(id);
                getEmployeeDetailDAO().delete(employeeDetail);
                getEmployeeDao().delete(employee);
            }
            getDepartmentDao().delete(department);

        } catch (DaoException e) {
            log.error(e, e);
        } catch (NullPointerException e) {
            log.error("Unable find employee:", e);
        }
        System.out.print("All Entity delete sucsecfully");
    }


    public static EmployeeDao getEmployeeDao() {
        if (employeeDao == null) {
            employeeDao = new EmployeeDao();
        }
        return employeeDao;
    }

    public static EmployeeDetailDAO getEmployeeDetailDAO() {
        if (employeeDetailDAO == null) {
            employeeDetailDAO = new EmployeeDetailDAO();
        }
        return employeeDetailDAO;
    }

    public static DepartmentDao getDepartmentDao() {
        if (departmentDAO == null) {
            departmentDAO = new DepartmentDao();
        }
        return departmentDAO;
    }
}

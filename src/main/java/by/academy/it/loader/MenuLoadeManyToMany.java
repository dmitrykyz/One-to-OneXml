package by.academy.it.loader;

import by.academy.it.db.DepartmentDao;
import by.academy.it.db.EmployeeDao;
import by.academy.it.db.EmployeeDetailDAO;
import by.academy.it.db.MeetingDao;
import by.academy.it.db.exceptions.DaoException;
import by.academy.it.pojos.Department;
import by.academy.it.pojos.Employee;
import by.academy.it.pojos.EmployeeDetail;
import by.academy.it.pojos.Meeting;
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
    private static MeetingDao meetingDao = null;

    public static void menu() throws DaoException {
        Employee employee = null;
        EmployeeDetail employeeDetail = null;
        Department department  = null;
        Meeting meeting = null;
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
                    System.out.println("-----------------------------");
                    System.out.println(employee.getEmployeeDetail());
                    System.out.println("-----------------------------");
                    System.out.println(employee.getDepartment());
                    System.out.println("-----------------------------");
                    Set<Meeting> meetingsTemp = employee.getMeetings();
                    for (Meeting meeting1: meetingsTemp) {
                        System.out.println("Meeting" + meeting1);
                        System.out.println("-----------------------------");
                    }
                    break;
                case 3:
                    employeeDetail = findEmployeeDetail();
                    System.out.println("-----------------------------");
                    System.out.println(employeeDetail.getEmployee());
                    System.out.println("-----------------------------");
                    System.out.println(employeeDetail.getEmployee().getDepartment());
                    System.out.println("-----------------------------");
                    Set<Meeting> meetingsTemp1 = employeeDetail.getEmployee().getMeetings();
                    for (Meeting meeting1: meetingsTemp1) {
                        System.out.println("Meeting" + meeting1);
                        System.out.println("-----------------------------");
                    }
                    break;
                case 4:
                    department = findDepartment();
                    System.out.println("-----------------------------");
                    Set<Employee> employeesTemp = department.getEmployees();
                    for (Employee employee1: employeesTemp) {
                        System.out.println("Employee" + employee1);
                        System.out.println("EmployeeDetail" + employee1.getEmployeeDetail());
                        System.out.println("-----------------------------");
                        Set<Meeting> meetingsTemp2 = employee1.getMeetings();
                        for (Meeting meeting1: meetingsTemp2) {
                            System.out.println("Meeting" + meeting1);
                            System.out.println("-----------------------------");
                        }
                    }
                    break;
                case 5:
                    meeting = findMeeting();
                    System.out.println("-----------------------------");
                    Set<Employee> employeesTempMeeting = meeting.getEmployees();
                    for (Employee employee1: employeesTempMeeting) {
                        System.out.println("Employee" + employee1);
                        System.out.println("-----------------------------");
                        System.out.println("EmployeeDetail" + employee1.getEmployeeDetail());
                        System.out.println("-----------------------------");
                        System.out.println("Department" + employee1.getDepartment());
                        System.out.println("-----------------------------");
                    }
                    break;
                case 6:
                    Meeting meeting1 = createMeeting();
                    Meeting meeting2 = createMeeting();
                    Set<Meeting> meetings = new HashSet<Meeting>();
                    meetings.add(meeting1);
                    meetings.add(meeting2);

                    Department department1 = createDepartment();
                    EmployeeDetail employeeDetail1 = createEmployeeDetail(employeeDetail);
                    employee = createEmployee(employeeDetail1);

                    getDepartmentDao().saveOrUpdate(department1);

                    employee.setEmployeeDetail(employeeDetail);

                    employee.setDepartment(department1);
                    Set<Employee> employees = new HashSet<Employee>();
                    employees.add(employee);
                    department1.setEmployees(employees);

                    employee.setMeetings(meetings);

                    employeeDetail1.setEmployee(employee);

                    getEmployeeDao().saveOrUpdate(employee);
                    getEmployeeDetailDAO().saveOrUpdate(employeeDetail1);
                    break;
                case 7:
                    //employee = loadEmployee();
                    break;
                case 8:
                    //flushSession();
                    break;
                case 9:
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
        System.out.println("        1. Delete All by DepatrmentID");
        System.out.println("        2. Get All by Employee ID");
        System.out.println("        3. Get All by EmployeeDetail ID");
        System.out.println("        4. Get All by Department ID");
        System.out.println("        5. Get All by Meeting ID");
        System.out.println("        6. Save or Update Meeting Department Employee Employee Detail");
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

    private static Meeting createMeeting() {
        System.out.println("Please enter Meeting description:");
        System.out.print("Meeting subject - ");

        Meeting meeting = new Meeting();
        Scanner scanner = new Scanner(System.in);
        String parameter = scanner.nextLine();
        meeting.setSubject(parameter);
        return meeting;
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

    public static Meeting findMeeting() {
        System.out.println("Get by Id. Please enter Meeting id:");
        System.out.print("Id - ");

        Scanner scanner = new Scanner(System.in);
        Meeting meeting = null;
        Integer id = scanner.nextInt();
        try {
            meeting = getMeetingDao().get(id);
        } catch (DaoException e) {
            log.error(e, e);
        } catch (NullPointerException e) {
            log.error("Unable find Meeting:", e);
        }
        System.out.print(meeting);
        return meeting;
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
            for (Employee employee1 : employees) {
                employee = getEmployeeDao().get(id);
                employeeDetail = getEmployeeDetailDAO().get(id);
                //getEmployeeDetailDAO().delete(employeeDetail);
                getEmployeeDao().delete(employee);
            }
            getDepartmentDao().delete(department);

        } catch (DaoException e) {
            log.error(e, e);
        } catch (NullPointerException e) {
            log.error("Unable find employee:", e);
        }
        System.out.print("Employee and EmployeeDetail delete sucsecfully");
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

    public static MeetingDao getMeetingDao() {
        if (meetingDao == null) {
            meetingDao = new MeetingDao();
        }
        return meetingDao;
    }
}

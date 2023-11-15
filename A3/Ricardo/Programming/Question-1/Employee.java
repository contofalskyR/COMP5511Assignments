
public class Employee implements Comparable<Employee> {
    // creating employee objects 
    String employeeId;
    String SIN;
    String name;
    String department; 
    String address; 
    double salary;

    // adding constructor
    public Employee(String employeeId, String SIN, String name, String department, String address, double salary) {
        this.employeeId = employeeId;
        this.SIN = SIN;
        this.name = name;
        this.department = department;
        this.address = address;
        this.salary = salary;
    }

    // comparing employee against another employee
    @Override
    public int compareTo(Employee other) {
        return this.employeeId.compareTo(other.employeeId);
    }

    // overriding toString method for better readability when printing objects
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", SIN='" + SIN + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                '}';
    }

    //will return employee 
    public String getEmployeeId() {
        return this.employeeId;
    }

    public String getSIN() {
        return this.SIN;
    }


    // overloaded constructor for search operations
    public Employee(String employeeId) {
        this.employeeId = employeeId;
    }

    
}


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

    // setting comparison criterion
    private static String comparisonCriterion = "ID"; // Default to ID

    public static void setComparisonCriterion(String criterion) {
        comparisonCriterion = criterion;
    }
    
    public static String getComparisonCriterion() {
        return comparisonCriterion;
    }

    // comparing employee against another employee
    //checks whether we are comparing against SIN or employee ID
    @Override
    public int compareTo(Employee other) {
        if (comparisonCriterion.equals("SIN")) {
            // null-safe comparison for SIN
            return compareNullableStrings(this.SIN, other.SIN);
        } else {
            // null-safe comparison for employeeId
            return compareNullableStrings(this.employeeId, other.employeeId);
        }
    }
    
    private static int compareNullableStrings(String a, String b) {
        if (a == null && b == null) {
            return 0; // both are null, treat as equal
        } else if (a == null) {
            return -1; // treat 'a' as 'less than' b
        } else if (b == null) {
            return 1; // treat 'a' as 'greater than' b
        } else {
            return a.compareTo(b); // both are non-null, use standard comparison
        }
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

import java.util.List;
import java.util.Comparator;

public class RecordManager {
    // class level variables 
    private static BinaryTree<Employee> tree = new BinaryTree<>();
    private static Comparator<Employee> comparator = Comparator.comparing(Employee::getEmployeeId);


    public static void sortRecords(List<Employee> employees, Comparator<Employee> comparator) {
        // sort employees based on their IDs 
         HeapSort.sort(employees, comparator);
         fillTreeWithEmployees(employees);
    }

    public static void displayRecords(List<Employee> employees) {
        //display all records
        for (Employee emp : employees) {
            System.out.println(emp); 
        }
    }

    public static void displayTree(){
        tree.inOrderTraversal();
    }


    public static void fillTreeWithEmployees(List<Employee> employees) {
        // clearing tree records
        tree.clear(); 
        for (Employee emp : employees) {
            tree.insert(emp);
        }
    }    

    public static void addRecord(List<Employee> employees, Employee employee) {
        // checking if the employee already exists in the tree to avoid duplicates
        if (tree.search(employee) == null) {
            tree.insert(employee); // adding to the binary tree
        } else {
            System.out.println("Employee with ID " + employee.getEmployeeId() + " already exists.");
        }
    }
    
    

    public static Employee searchRecord(String employeeId) {
        Employee dummyEmployee = new Employee(employeeId); // create a dummy employee with the ID
        return tree.search(dummyEmployee); // use the tree's search method to find the actual employee
    }
    

    public static void updateRecord(List<Employee> employees, String employeeId, Employee updatedEmployee) {
        // use the tree's update method to update the record
        tree.updateRecord(employees, tree, employeeId, updatedEmployee, comparator);
    }
    
    
    
public static void delete(String employeeId){
    Employee deleteRecord = new Employee(employeeId);
    tree.delete(deleteRecord);
}    

    
}

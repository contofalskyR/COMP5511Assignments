import java.util.List;
import java.util.Comparator;

public class RecordManager {
    // class level variables 
    private static BinaryTree<Employee> tree = new BinaryTree<>();
    private static Comparator<Employee> comparator = Comparator.comparing(Employee::getEmployeeId);


    public static void sortRecords(List<Employee> employees, Comparator<Employee> comparator) {
        // sort employees based on their IDs 
         HeapSort.sort(employees, comparator);
    }

    public static void displayRecords(List<Employee> employees) {
        //display all records
        for (Employee emp : employees) {
            System.out.println(emp); 
        }
    }

    public static void addRecord(List<Employee> employees, Employee employee) {
        // add employee to the list
         employees.add(employee);
        tree.insert(employee); // add to the binary tree
        HeapSort.sort(employees, comparator); // sort the list again
    }

    public static Employee searchRecord(String employeeId) {
        Employee dummyEmployee = new Employee(employeeId); // create a dummy employee with the ID
        return tree.search(dummyEmployee); // use the tree's search method to find the actual employee
    }
    

    public static void updateRecord(List<Employee> employees, String employeeId, Employee updatedEmployee) {
        // find and update the employee in the list
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmployeeId().equals(employeeId)) {
                // update the binary tree
                tree.delete(employees.get(i)); // remove old employee from the tree
                tree.insert(updatedEmployee); // add updated employee to the tree

                employees.set(i, updatedEmployee); // update the list
                break;
            }
        }
        // Re-sort the list
        HeapSort.sort(employees, comparator);

    }
    

    
}

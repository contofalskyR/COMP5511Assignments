import java.util.List;
import java.util.Comparator;

public class RecordManager {
    // class level variables 
    private static boolean isInitialSortDone = false;
    private static BinaryTree<Employee> tree = new BinaryTree<>();
 


    private static String lastUsedCriterion = null;

    public static void sortRecords(List<Employee> employees, String criterion) {
    // checks if the current tree matches with previous criterion used to sort the tree
    if (lastUsedCriterion == null || !lastUsedCriterion.equals(criterion)) {
        lastUsedCriterion = criterion;
        Employee.setComparisonCriterion(criterion.equals("SIN") ? "SIN" : "EmployeeID");

        // clear the tree and repopulate it
        tree.clear();
        HeapSort.sort(employees);             
        fillTreeWithEmployees(employees);
    }

    // display the sorted tree
    System.out.println("Sorted employees:");
    displayTree();
    }
    

    public static void displayRecords(List<Employee> employees) {
        //display all records
        tree.clear();
        for (Employee emp : employees) {
            System.out.println(emp); 
        }
    }

    public static void displayTree(){
        tree.inOrderTraversal();
    }


    public static void fillTreeWithEmployees(List<Employee> employees) {
        // fill the tree with employees
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
    

    public static void updateRecord(String employeeId, Employee updatedEmployee) {
        // temporarily sets the comparison criterion to "EmployeeID" for this particular action
        String originalCriterion = Employee.getComparisonCriterion(); // Assuming a getter method exists
        Employee.setComparisonCriterion("EmployeeID");

        // Perform the update
        Employee targetEmployee = searchRecord(employeeId);
        if (targetEmployee != null) {
            tree.updateRecord(employeeId, updatedEmployee);
            System.out.println("Employee updated successfully.");
        } else {
            System.out.println("Employee with ID " + employeeId + " not found.");
        }

        // restores the original comparison criterion
        Employee.setComparisonCriterion(originalCriterion);
    }
    
    
    public static void delete(String employeeId){
        String originalCriterion = Employee.getComparisonCriterion();
        Employee.setComparisonCriterion("EmployeeID");
    
        Employee deleteRecord = new Employee(employeeId);
        tree.delete(deleteRecord);
    
        Employee.setComparisonCriterion(originalCriterion); // restore original criterion
    }
    
    
}

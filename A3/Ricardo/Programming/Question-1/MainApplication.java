import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApplication {

    public static void main(String[] args) {
        //Obtains input from the user 
        Scanner scanner = new Scanner(System.in);

        //Read records from file 
        List<Employee> employees = EmployeeFileReader.readEmployeesFromFile("employee.txt");
        
        
        // setting variable for interface 
        boolean exit = false;

        while (!exit) {  
            System.out.println("\nChoose an option:");
            System.out.println("1. Display Records\n2. Display Sorted Records\n3. Add a Record\n4. Update a Record\n5. Search Record\n6. Delete a Record\n7.Exit");
            int choice = scanner.nextInt();
            switch (choice) { 
                case 1:
                System.out.println("Unsorted employees:");
                RecordManager.displayRecords(employees); 
                break;
                case 2:
                System.out.println("Sort by (1) Employee ID or (2) SIN?");
                // read users input 
                String choiceUser = scanner.next();
                String sortCriterion;
 
                if (choiceUser.equals("1")) {
                    sortCriterion = "EmployeeID";
                } else if (choiceUser.equals("2")) {
                    sortCriterion = "SIN";
                    System.out.println("went through 2");
                } else {
                    System.out.println("Invalid input. Defaulting to Employee ID.");
                    sortCriterion = "EmployeeID";

                }
                RecordManager.sortRecords(employees, sortCriterion);            
                break;
                case 3:
                scanner.nextLine(); // consumes leftover newline from previous inputs
                System.out.println("Enter Employee ID (format E000):");
                String employeeId = scanner.nextLine();
                if (!employeeId.matches("E\\d{3}")) {
                System.out.println("Invalid Employee ID format.");
                break;
                }
                System.out.println("Enter SIN (format SIN000):");
                String sin = scanner.nextLine();
                if (!sin.matches("SIN\\d{3}")) {
                System.out.println("Invalid SIN format.");
                break;
                }   
                System.out.println("Enter name:");
                String name = scanner.nextLine();

                System.out.println("Enter department:");
                String department = scanner.nextLine();

                System.out.println("Enter address:");
                String address = scanner.nextLine();

                System.out.println("Enter salary:");
                while (!scanner.hasNextDouble()) {
                    System.out.println("Please enter a valid salary (numeric value):");
                    scanner.next(); // invalid input
                }
                double salary = scanner.nextDouble();
                Employee newEmployee = new Employee(employeeId, sin, name, department, address, salary);
                RecordManager.addRecord(employees, newEmployee);
                System.out.println("\nEmployee added successfully.");
                RecordManager.displayTree();
                break;
                case 4:
                scanner.nextLine(); // consume the leftover newline from previous nextInt()
                
                System.out.println("Enter the Employee ID you wish to update (format E000):");
                String updateEmployeeId = scanner.nextLine();
                if (!updateEmployeeId.matches("E\\d{3}")) {
                    System.out.println("Invalid Employee ID format.");
                    break;
                }

                System.out.println("Enter new SIN (format SIN000):");
                String updateSin = scanner.nextLine();
                if (!updateSin.matches("SIN\\d{3}")) {
                    System.out.println("Invalid SIN format.");
                    break;
                }

                System.out.println("Enter new Name:");
                String updateName = scanner.nextLine();

                System.out.println("Enter new Department:");
                String updateDepartment = scanner.nextLine();

                System.out.println("Enter new Address:");
                String updateAddress = scanner.nextLine();

                System.out.println("Enter new Salary:");
                while (!scanner.hasNextDouble()) {
                    System.out.println("Please enter a valid salary (numeric value):");
                    scanner.next(); // Consumes the invalid input
                }
                double updateSalary = scanner.nextDouble();
                scanner.nextLine(); // Consume the leftover newline from nextDouble()

                Employee updatedEmployee = new Employee(updateEmployeeId, updateSin, updateName, updateDepartment, updateAddress, updateSalary);
                RecordManager.updateRecord(updateEmployeeId, updatedEmployee);
                System.out.println("\nEmployee updated successfully.");
                System.out.println("\nDisplaying updated records:");
                RecordManager.displayTree();
                break;
                case 5:
                 Employee employee = RecordManager.searchRecord("E005");
                 System.out.println(employee);
                if (employee != null) {
                    // Employee found, print details
                    System.out.println("\nEmployee Found:");
                    System.out.println("Employee ID: " + employee.employeeId);
                    System.out.println("SIN: " + employee.SIN);
                    System.out.println("Name: " + employee.name);
                    System.out.println("Department: " + employee.department);
                    System.out.println("Address: " + employee.address);
                    System.out.println("Salary: " + employee.salary);
                } else {
                    // Employee not found
                    System.out.println("Employee with ID E001 not found.");
                }
                break;
                case 6:
                scanner.nextLine(); // consumes any leftover newline character
            
                System.out.println("Enter the Employee ID you wish to delete (format E000):");
                String deleteEmployeeId = scanner.nextLine();
                
                // validates the format of the Employee ID
                if (!deleteEmployeeId.matches("E\\d{3}")) {
                    System.out.println("Invalid Employee ID format.");
                    break;
                }
                RecordManager.delete(deleteEmployeeId);
                System.out.println("\nDisplaying updated records after deleting " + deleteEmployeeId + ":");
                RecordManager.displayTree(); 
                break;
                case 7: 
                exit = true; 
                break;
                default: 
                    System.out.println("Invalid option. Please choose again.");
            }
        }
            scanner.close();


        // //Populating the tree  
        // System.out.println("Unsorted employees:");
        // RecordManager.displayRecords(employees); 

        // // Sort and display initial records
        // // RecordManager.sortRecords(employees, Comparator.comparing(Employee::getEmployeeId));
        // RecordManager.sortRecords(employees, Comparator.comparing(Employee::getEmployeeId));
        // System.out.println("\nSorted Employees:");
        // RecordManager.displayRecords(employees); 

        // // Add a new record
        // Employee newEmployee = new Employee("E005", "SIN000", "New Employee", "Engineering", "123 Address St", 50000.0);
        // RecordManager.addRecord(employees, newEmployee);
        // System.out.println("\nSorted Employees + new record:");
        // RecordManager.displayTree(); 


        // // Search for a record 
        // Employee employee = RecordManager.searchRecord("E002");
        // if (employee != null) {
        //     // Employee found, print details
        //     System.out.println("\nEmployee Found:");
        //     System.out.println("Employee ID: " + employee.employeeId);
        //     System.out.println("SIN: " + employee.SIN);
        //     System.out.println("Name: " + employee.name);
        //     System.out.println("Department: " + employee.department);
        //     System.out.println("Address: " + employee.address);
        //     System.out.println("Salary: " + employee.salary);
        // } else {
        //     // Employee not found
        //     System.out.println("Employee with ID E001 not found.");
        // }

        // // Update a record 
        //     Employee updateEmployee = new Employee("E005", "SIN005", "Updated Employee", "Engineering", "123 Address St", 50000.0);
        //     RecordManager.updateRecord("E005", updateEmployee);
        //     System.out.println("\nDisplaying updated records:");
        //     RecordManager.displayTree(); 

        
        // // Delete a record 
        // RecordManager.delete("E005");
        // System.out.println("\nDisplaying updated records after deleting E005:");
        // RecordManager.displayTree(); 


    // Add interface 
    // Test full application with employee ID 
    // Fix the order the employees are being added 
    // Test adding a record at the beginning
    // Add fixed interface with new features 

    // Sort by either SIN or employeeID

    // Remove Employee{employeeId}
    // Make the "after deleting E" dynamic
    // Check final document
    // Push theoretical component 

   
    }

    // will read records from the book.txt file
    public class BookFileReader {

        // will read records from the employee.txt file and convert into a list of book objects
        public static List<Book> readBooksFromFile(String fileName) {
            List<Book> books = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");
                    Book book = new Book(fields[0].trim(), fields[1].trim(), fields[2].trim(), fields[3].trim(), fields[4].trim(), Double.parseDouble(fields[5].trim()));
                    books.add(book);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return books;
        }
    }

    // will read records from the employee.txt file and convert into a list of Employee objects
    public class EmployeeFileReader {
        public static List<Employee> readEmployeesFromFile(String fileName) {
            List<Employee> employees = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                // Read and discard the first line (header)
                String headerLine = reader.readLine();
    
                String line;
                while ((line = reader.readLine()) != null) {
                    // skip empty lines.
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    String[] fields = line.split(",");
                    if (fields.length != 6) { // ensure that there are exactly 6 fields per employee record
                        System.err.println("Invalid employee record: " + line);
                        continue;
                    }
                    // parse the fields and create a new Employee object.
                    Employee employee = new Employee(
                        fields[0].trim(), // Employee-ID
                        fields[1].trim(), // SIN
                        fields[2].trim(), // Name
                        fields[3].trim(), // Department
                        fields[4].trim(), // Address
                        Double.parseDouble(fields[5].trim()) // Salary
                    );
                    employees.add(employee);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.err.println("There was an error parsing the salary. Please check the input file format.");
                e.printStackTrace();
            }
            return employees;
        }
        
    }
    
}
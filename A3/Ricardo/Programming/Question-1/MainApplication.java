import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class MainApplication {

    public static void main(String[] args) {
        
        //Read records from file 
        List<Employee> employees = EmployeeFileReader.readEmployeesFromFile("employee.txt");
        //Populating the tree  

        System.out.println("Unsorted employees:");
        RecordManager.displayRecords(employees); 

        // Sort and display initial records
        // RecordManager.sortRecords(employees, Comparator.comparing(Employee::getEmployeeId));
        RecordManager.sortRecords(employees, Comparator.comparing(Employee::getSIN));
        System.out.println("\nSorted Employees:");
        RecordManager.displayRecords(employees); 

        // Add a new record
        Employee newEmployee = new Employee("E005", "SIN000", "New Employee", "Engineering", "123 Address St", 50000.0);
        RecordManager.addRecord(employees, newEmployee);
        System.out.println("\nSorted Employees + new record:");
        RecordManager.displayTree(); 


        // Search for a record 
        Employee employee = RecordManager.searchRecord("E005");
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

        // Update a record 
            Employee updateEmployee = new Employee("E005", "123456789", "Updated Employee", "Engineering", "123 Address St", 50000.0);
            RecordManager.updateRecord(employees, "E005", updateEmployee);
            System.out.println("\nDisplaying updated records:");
            RecordManager.displayTree(); 

        
        // Delete a record 
        RecordManager.delete("E005");
        System.out.println("\nDisplaying updated records after deleting E005:");

        RecordManager.displayTree(); 


    // Add interface 

   
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
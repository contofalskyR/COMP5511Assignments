import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Records{
    Record[] records;
    int totalRecords;
    File file;
    //Use the isFull variable to keep from overfilling the array
    boolean isFull;


    public Records(int size, String recordFile){
        this.records = new Record[size];
        this.totalRecords = 0;
        String data;
        Record record;

        try {
            this.file = new File(recordFile);
            Scanner scanner = new Scanner(file);

            //not reading the first line of file
            scanner.nextLine();

            //Add isFull code 
            while (scanner.hasNextLine()) {
                data = scanner.nextLine();
                record = stringToRecord(data);
                records[totalRecords]=record;
                totalRecords++;             
            }
            scanner.close();
          } catch (FileNotFoundException e) {
            System.out.println("No records file found");
        }
    }


    public void printRecords(){
        for (Record record : records){
            if (record==null){
                return;
            }
            record.printFields();
        }
    }


    public void addRecord(){
        Record record;
        String data;
        Scanner scanner = new Scanner(System.in);
        FileWriter writer = null;

        System.out.println("Please input your emplyee data in the following format:");
        System.out.println("Employee-ID, SIN, Name, Department, Address, Salary");

        data = scanner.nextLine();
        record = stringToRecord(data);
        records[totalRecords] = record;
        totalRecords++;
        try{
            writer = new FileWriter(file, true);
            writer.write("\n"+data);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                writer.close();

            } catch (IOException e){
                e.printStackTrace();
            }
        }

        //deal with isFull
        //deal with invalid input
        //write to file
    }

    public void deleteRecord(Record record){
        //find record in array
        //find record in file
        //delete record
    }

    public static Record stringToRecord(String data){
        Scanner scanner = new Scanner(data).useDelimiter(",\\s*");
        Record record = new Record(scanner.next(),  
            scanner.next(), 
            scanner.next(), 
            scanner.next(),
            scanner.next(), 
            scanner.next());
        return record;
        //deal with full 
    }
    public void heapify(String field){

    }
    public void heapSort(String field){

    }

    public static void main(String[] args){
        Records employees = new Records(50, "records.txt");
        //Write something to allow user to input/delete data during the running of the program
        employees.addRecord();
        employees.printRecords();
    }

}


class Record{
    String EmployeeID;
    String SIN;
    String Name; 
    String Department;
    String Address;
    String Salary;

    public Record(String EmployeeID,String SIN,String Name,String Department,String Address,String Salary){
        this.EmployeeID=EmployeeID;
        this.SIN = SIN;
        this.Name=Name;
        this.Department=Department;
        this.Address = Address;
        this.Salary=Salary;
    }


    public void printFields() {
        System.out.println("EmployeeID: " + EmployeeID);
        System.out.println("SIN: " + SIN);
        System.out.println("Name: " + Name);
        System.out.println("Department: " + Department);
        System.out.println("Address: " + Address);
        System.out.println("Salary: " + Salary);
        System.out.println();
    }
}

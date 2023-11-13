import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Records{
    Record[] records;
    int totalRecords;
    File file;
    int size;
    //Use the isFull variable to keep from overfilling the array
    boolean isFull;


    public Records(int size, String recordFile){
        this.size = size; //resize method?
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

    public void writeRecordToFile(String data){
        FileWriter writer = null;
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
    }


    public void addRecord(){
        Record record;
        String data;
        Scanner scanner = new Scanner(System.in);


        System.out.println("Please input your emplyee data in the following format:");
        System.out.println("Employee-ID, SIN, Name, Department, Address, Salary");

        data = scanner.nextLine();
        record = stringToRecord(data);
        records[totalRecords] = record;
        totalRecords++;
        writeRecordToFile(data);

        //deal with isFull
        //deal with invalid input
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

    public void swap(int i, int j){
        Record temp = records[i];
        records[i]=records[j];
        records[j]=temp;
    }

    public void heapify(int field, int n, int i){
		int largest = i; // Initialize largest as root
		int l = 2 * i + 1; // left = 2*i + 1
		int r = 2 * i + 2; // right = 2*i + 2

		// If left child is smaller than root
		if (l < n && records[l].recordArr[field].compareTo(records[largest].recordArr[field])>0)
			largest = l;

		// If right child is smaller than largest so far
		if (r < n && records[r].recordArr[field].compareTo(records[largest].recordArr[field])>0)
			largest = r;

		// If largest is not root
		if (largest != i) {
			//int temp = arr[i];
			//arr[i] = arr[largest];
			//arr[largest] = temp;
            swap(i,largest);

			// Recursively heapify the affected sub-tree
			heapify(field, n, largest);
		}
	}

    public void heapSort(String field){
        int n = totalRecords;
        int fieldCode;

        switch(field){
            case("EmployeeID"):
                fieldCode = 0;
                break;
            case("SIN"):
                fieldCode = 1;
                break;
            case("Name"):
                fieldCode = 2;
                break;
            case("Department"):
                fieldCode = 3;
                break;
            case("Address"):
                fieldCode = 4;
                break;
            case("Salary"):
                fieldCode = 5;
                break;
            default: fieldCode = 0;
            
        }

		// Build heap
		for (int i = n / 2 - 1; i >= 0; i--)
			heapify(fieldCode, n, i);

		// One by one extract an element from heap
		for (int i = n - 1; i >= 0; i--) {
			
			// Move current root to end
			swap(0,i);

			// call min heapify on the reduced heap
			heapify(fieldCode, i, 0);
		}
	}


    public static void main(String[] args){
        Records employees = new Records(50, "records.txt");
        employees.heapSort("Salary");
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

    //This variable helps sort by field
    String[] recordArr;

    public Record(String EmployeeID,String SIN,String Name,String Department,String Address,String Salary){
        this.EmployeeID=EmployeeID;
        this.SIN=SIN;
        this.Name=Name;
        this.Department=Department;
        this.Address = Address;
        this.Salary=Salary;
        this.recordArr = new String[]{EmployeeID,SIN,Name,Department,Address,Salary};

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

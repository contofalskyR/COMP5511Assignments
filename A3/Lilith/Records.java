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
    //resize at a certain point
    boolean isFull;
    Node BSTRoot;


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
        heapSort("EmployeeID");
        BSTRoot = constructBST(records,0,totalRecords-1);
        //heapsort
        //construct bst
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

        //insert into sorted list
        //insert into BST
        //maybe add a check for remaking the bst
    }

    //Some fields may have multiple variables
    //with the same value
    public Record getRecord(String field, String value){
        int fieldIndex = fieldIndex(field);
        Node currNode = BSTRoot;
        String currField;

        
        while(currNode != null){
            currField = currNode.record.recordArr[fieldIndex];
            if(currField.compareTo(value)==0){
                return currNode.record;
            }            
            if(currField.compareTo(value)>0){
                currNode = currNode.left;
            }
            else{
                currNode = currNode.right;
            }
        }
        return null;
    }

    public void deleteRecord(Record record){
        //find record in array
        //find record in file
        //delete record
        heapSort("EmployeeID");
        //do binary search to find record
        //delete record
        //remove from sorted 
        //remove from bst
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

    public int fieldIndex(String field){
        switch(field){
            case("EmployeeID"):
                return 0;
            case("SIN"):
                return 1;
            case("Name"):
                return 2;
            case("Department"):
                return 3;
            case("Address"):
                return 4;
            case("Salary"):
                return 5;
            default: return 0;
        }
    }

    public void heapSort(String field){
        int n = totalRecords;
        int fieldCode = fieldIndex(field);

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

        //AT THE END, CREATE THE BST
	}
    public Node constructBST(Record[] records, int start, int end){
        if (start>=end){
            return null;
        }
        //find median
        //create Node with median
        //for both side around median, do the same until subarray is empty

        int median = (start+end)/2;
        Node root = new Node(records[median],median);
        root.left = (constructBST(records, start, median-1));
        root.right = (constructBST(records, median+1, end));
        return root;
    }




    public static void main(String[] args){
        Records employees = new Records(50, "records.txt");
        employees.heapSort("Salary");
        employees.printRecords();
        employees.getRecord("EmployeeID", "DCOOP0123").printFields();
    }

}

class Node{
    Record record;
    int index;
    Record next;
    Node left;
    Node right;
    public Node(Record record, int index){
        this.record = record;
        this.index = index;
        this.next = null;
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
        this.EmployeeID=EmployeeID.strip();
        this.SIN=SIN.strip();
        this.Name=Name.strip();
        this.Department=Department.strip();
        this.Address = Address.strip();
        this.Salary=Salary.strip();
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

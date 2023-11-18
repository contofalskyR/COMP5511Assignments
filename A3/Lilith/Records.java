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
    int sortedBy;


    public Records(int size, String recordFile){
        this.size = size; //resize method?
        this.records = new Record[size];
        this.totalRecords = 0;
        String data;

        try {
            this.file = new File(recordFile);
            Scanner scanner = new Scanner(file);

            //not reading the first line of file
            scanner.nextLine();

            //Add isFull code 
            while (scanner.hasNextLine()) {
                data = scanner.nextLine();
                addRecord(data);
        
            }
            scanner.close();
          } catch (FileNotFoundException e) {
            System.out.println("No records file found");
        }
        BSTRoot = constructBST(records, 0, totalRecords-1);
        heapSort("EmployeeID");
    }


    public void extendArray(){
        size = size*2;
        Record[] biggerRecords = new Record[size];
        for (int i=0; i<totalRecords;i++){
            biggerRecords[i] = records[i];
        }

        records = biggerRecords;

    }
    public void printRecords(){
        for (int i=0;i<totalRecords;i++){
            printFields(records[i]);
        }
    }

    public void addRecord(String data){
        Record record;

        record = stringToRecord(data);
        records[totalRecords]=record;
        totalRecords++;  
        if(size==totalRecords){
            extendArray();
        }   

    }


    public void addRecord(){
        String data;
        Scanner scanner = new Scanner(System.in);


        System.out.println("Please input your emplyee data in the following format:");
        System.out.println("Employee-ID, SIN, Name, Department, Address, Salary");

        data = scanner.nextLine();
        addRecordToSorted(data);

        //insert into sorted list
        //insert into BST
        //maybe add a check for remaking the bst
    }

    //inserts a record into the records array at a given index
    public void insertToList(Record record, int index){
        totalRecords++;
        if(size<=totalRecords){
            extendArray();
        }
        for (int i=totalRecords;i>index;i--){
            records[i]=records[i-1];
        }
        records[index] = record;
    }

    public void addRecordToSorted(Record record){
        String insertRec = record.recordArr[sortedBy];

        int index = getArrayIndex(insertRec);

        insertToList(record, index);
        BSTRoot = insertToBST(record, BSTRoot);


    }


    public void addRecordToSorted(String data){
        Record record = stringToRecord(data);
        String insertRec = record.recordArr[sortedBy];

        int index = getArrayIndex(insertRec);

        insertToList(record, index);
        BSTRoot = insertToBST(record, BSTRoot);


    }

    public int getArrayIndex(String insertRec){
        String arrRec;

        int start = 0;
        int end = totalRecords-1;
        int mid = (start+end)/2;

        while(start<=end){
            arrRec = records[mid].recordArr[sortedBy];
            if(insertRec.compareTo(arrRec)>0){
                start = mid+1;
                mid = (start+end)/2;
            }else{
                if(insertRec.compareTo(arrRec)<0){
                    end = mid-1;
                    mid=(start+end)/2;
                }
                else{
                    return mid;
                }
            }
        }
        if(mid==0 && insertRec.compareTo(records[mid].recordArr[sortedBy])<0){
            return mid;
        }
        return mid+1;
    }

    //inserts new record node to the BST based on second key
    public Node insertToBST(Record record, Node root){ 
        if (root==null){
            root = new Node(record);
            return root;
        }
        String toInsert = record.recordArr[1];
        String rootVal = root.record.recordArr[1];

        if(toInsert.compareTo(rootVal)<0){
            root.left = insertToBST(record, root.left);
        }else{
            root.right = insertToBST(record, root.right);
            
        } 
        return root;
    }


    public Node findRecord(int field, String fieldValue){

        Node currNode = BSTRoot;
        while(currNode!=null){
            String currVal = currNode.record.recordArr[field];
            if(currVal.equals(fieldValue)){
                return currNode;
            }
            if(currVal.compareTo(fieldValue)>0){
                currNode = currNode.left;
            }
            else{
                currNode = currNode.right;
            }
        }return null;
    }

    public void printRecord(String field, String fieldValue){
        int fieldIndex = fieldIndex(field);
        Node foundRecord = findRecord(fieldIndex, fieldValue);
        if(foundRecord==null){
            System.out.println("record not found");
        }else{
            System.out.println("Record found: ");
            printFields(foundRecord.record);
        }
    }

    public int deleteRecord(Record record){
        String insertRec = record.recordArr[sortedBy];
        String arrRec;
        int start = 0;
        int end = totalRecords-1;
        int mid = (start+end)/2;

        int recordIndex = totalRecords+1;

        while(start<=end){// && end != 0 && start != totalRecords-1){
            arrRec = records[mid].recordArr[sortedBy];
            if(insertRec.compareTo(arrRec)>0){
                start = mid+1;
                mid = (start+end)/2;
            }else{
                if(insertRec.compareTo(arrRec)<0){
                    end = mid-1;
                    mid=(start+end)/2;
                }
                else{
                    recordIndex = mid;
                    break;
                }
            }
           
        }

        if(recordIndex>totalRecords){
            System.out.println("Cannot delete record. Does not exist");
            return 0;
        }else{
            for(int i = recordIndex; i<totalRecords;i++){
                records[i]=records[i+1];
            }
            totalRecords--;
        }

        BSTRoot = removeFromBST(BSTRoot, record);
        return 1;
        
    }

    public Node removeFromBST(Node root, Record record){
        if(root==null){
            return root;
        }

        String rootVal = root.record.recordArr[1];
        String recordVal = record.recordArr[1];

        if(rootVal.compareTo(recordVal)>0){
            root.left = removeFromBST(root.left, record);
        }else{
            if(rootVal.compareTo(recordVal)<0){
                root.right = removeFromBST(root.right, record);
            }else{
                if (root.left==null && root.right==null){
                    return null;
                }else{
                    if(root.left==null || root.right==null){
                        if(root.left==null){
                            Node temp = root.right;
                            root= null;
                            return temp;
                        }else{
                            Node temp = root.left;
                            root = null;
                            return temp;
                        }
                    }else{
                        Node parent = null;
                        Node child = root.right;
        
                        while(child.left!=null){
                            parent = child;
                            child = child.left;
                        }
        
                        if(parent!=null){
                            parent.left = child.right;
                        }else{
                            root.right = child.right;
                        }
        
                        root.record = child.record;
                        child=null;
                    }
                    
                }

            }
        }
        

        return root;
    }

    public void updateRecord(Record oldRecord, Record newRecord){
        if(deleteRecord(oldRecord)==0){
            System.out.println("Record to update does not exist");
        }else{
            addRecordToSorted(newRecord);
        }
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

    public void heapSort(int fieldCode){
        int n = totalRecords;

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
        this.sortedBy = fieldCode;
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
        this.sortedBy = fieldCode;
	}

    public void printInOrder(Node root){
        if(root==null){
            return;
        }
        printInOrder(root.left);
        printFields(root.record);
        printInOrder(root.right);
    }

    //constructs a BST from a sorted array
    public Node constructBST(Record[] records, int start, int end){

        //heapsorts the array of records based on the second field
        heapSort(1);

        if (start>end){
            return null;
        }
        //find median
        //create Node with median
        //for both sides around median, do the same until subarray is empty
        int median = (start+end)/2;
        Node root = new Node(records[median]);
        root.left = (constructBST(records, start, median-1));
        root.right = (constructBST(records, median+1, end));
        return root;
    }


    public static void printFields(Record record) {
        if (record == null){
            System.out.println("This record does not exist\n");
            return;
        }
        System.out.println("EmployeeID: " + record.EmployeeID);
        System.out.println("SIN: " + record.SIN);
        System.out.println("Name: " + record.Name);
        System.out.println("Department: " + record.Department);
        System.out.println("Address: " + record.Address);
        System.out.println("Salary: " + record.Salary);
        System.out.println();
    }


    public static void runSystem(){
        Records employees = new Records(50, "records.txt");
        employees.addRecordToSorted("TJONES0567, 998301928, Thomas Jones, Operations, 202 Operations Road, 68000");
        employees.addRecordToSorted("ABC0567, 123456787, ABC, Operations, 202 Operations Road, 68000");
        employees.addRecordToSorted("CDE567, 123456785, CDE, Operations, 202 Operations Road, 68000");
        employees.addRecordToSorted("ZZZ567, 123456788, ZZZ, Operations, 202 Operations Road, 68000");
        employees.addRecordToSorted("LMQ, 123456783, LMK, Operations, 202 Operations Road, 68000");

        employees.updateRecord(stringToRecord("ABC0567, 123456787, ABC, Operations, 202 Operations Road, 68000"), stringToRecord("ABC67810, 123456787, ABCGF, Operations, 202 Operations Road, 68000"));
        //employees.deleteRecord(stringToRecord("ABC0567, 123456787, ABC, Operations, 202 Operations Road, 68000"));
        //employees.deleteRecord(stringToRecord("ZZZ567, 123456788, ZZZ, Operations, 202 Operations Road, 68000"));
        //employees.deleteRecord(stringToRecord("ZZZ567, 123456788, Thomas Jones, Operations, 202 Operations Road, 68000"));
        //employees.deleteRecord(stringToRecord("ABC0567, 123456787, Thomas Jones, Operations, 202 Operations Road, 68000"));
        //employees.deleteRecord(stringToRecord("CDE567, 123456785, Thomas Jones, Operations, 202 Operations Road, 68000"));
        //employees.deleteRecord(stringToRecord("ABC0567, 123456789, Thomas Jones, Operations, 202 Operations Road, 68000"));
        //employees.printRecords();
        //employees.printRecords();
        employees.printInOrder(employees.BSTRoot);


        

    }

    public static void main(String[] args){
        
        runSystem();
        //employees.heapSort("EmployeeID");
        //employees.printRecords();
        //printFields(employees.getRecord("EmployeeID", "DCP0123"));
        
        
    }

}

class Node{
    Record record;
    Node left;
    Node right;
    public Node(Record record){
        this.record = record;
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

}

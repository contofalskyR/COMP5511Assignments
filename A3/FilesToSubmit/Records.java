import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;

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
        if(record==null){
            return;
        }
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
        if(record==null){
            return;
        }
        String insertRec = record.recordArr[sortedBy];

        int index = getArrayIndex(insertRec);

        insertToList(record, index);
        BSTRoot = insertToBST(record, BSTRoot);


    }


    public void addRecordToSorted(String data){
        Record record = stringToRecord(data);
        if(record==null){
            return;
        }
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
            System.out.println(currVal);
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
        if(field=="EmployeeID"){
            int start = 0;
            int end = totalRecords-1;
            int mid = (start+end)/2;
            while(start<=end){
                if(fieldValue.compareTo(records[mid].EmployeeID)>0){
                    start=mid+1;
                    mid = (start+end)/2;
                }else{
                    if(fieldValue.compareTo(records[mid].EmployeeID)<0){
                        end=mid-1;
                        mid = (start+end)/2;
                    }else{
                        printFields(records[mid]);
                        System.out.println("Record found: ");
                        return;
                    }
                }

            }System.out.println("record not found");
            return;
        }
        Node foundRecord = findRecord(1, fieldValue);
        if(foundRecord==null){
            System.out.println("record not found");
        }else{
            System.out.println("Record found: ");
            printFields(foundRecord.record);
        }
    }

    public int deleteRecord(Record record){
        if(record==null){
            return 0;
        }
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
        if(newRecord==null){
            return;
        }
        if(deleteRecord(oldRecord)==0){
            System.out.println("Record to update does not exist");
        }else{
            addRecordToSorted(newRecord);
        }
    }



    public static Record stringToRecord(String data){
        Scanner scanner = new Scanner(data).useDelimiter(",\\s*");
        try{
            Record record = new Record(scanner.next(),  
            scanner.next(), 
            scanner.next(), 
            scanner.next(),
            scanner.next(), 
            scanner.next());
        return record;
        }catch(NoSuchElementException e){
            System.out.println("Uh oh, you didn't put the right number of fields");
            return null;
        }

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
        Scanner scanner = new Scanner(System.in);
        Records employees = new Records(50, "records.txt");



        String input;
        String input2;

        while(true){
            System.out.println("\nPlease input the number corresponding to one of the following options:");
            System.out.println("1) Print records");
            System.out.println("2) Add a record");
            System.out.println("3) Delete a record");
            System.out.println("4) Find a specific record");
            System.out.println("5) Update a record");
            System.out.println("Type any other character to quit the program");
            input = scanner.next();
            scanner.nextLine();



            switch(input){
                case "1":
                    System.out.println("Select 1 to print by EmployeeID or 2 to print by SIN number");
                    input2 = scanner.next();
                    if(input2.endsWith("2")){
                        employees.printInOrder(employees.BSTRoot);
                    }else{
                        employees.printRecords();
                    }
                    break;
                case"2":
                    System.out.println("Input your record, comma separated, in the following order: ");
                    System.out.println("Employee-ID, SIN, Name, Department, Address, Salary");
                    input2=scanner.nextLine();
                    employees.addRecordToSorted(input2);
                    break;
                case "3":
                    System.out.println("Input the record you would like to delete, comma separated, in the following order: ");
                    System.out.println("Employee-ID, SIN, Name, Department, Address, Salary");
                    input2 = scanner.nextLine();
                    Record record = employees.stringToRecord(input2);
                    System.out.println();
                    employees.deleteRecord(record);
                    break;
                case"4":
                    System.out.println("Select 1 to search by EmployeeID or 2 to search by SIN number");
                    input2 = scanner.next();
                    if(input2.equals("2")){
                        System.out.println("Input the SIN number you are searching for: ");
                        String input3 = scanner.next();
                        System.out.println();
                        employees.printRecord("SIN",input3.strip());
                    }else{
                        System.out.println("Input the EmployeeID you are searching for: ");
                        String input3 = scanner.next();
                        System.out.println();
                        employees.printRecord("EmployeeID", input3.strip());
                    }
                    break;

                case "5":
                    System.out.println("Input the SIN number of the record you would like to update: ");
                    input2 = scanner.nextLine();
                    Node node = employees.findRecord(1, input2);
                    if(node==null){
                        System.out.println("Uh oh, there isn't a record with that sin number");
                        break;
                    }

                    Record r1 = node.record;
                    
                    System.out.println("Input the updated record, comma separated, in the following order: ");
                    System.out.println("Employee-ID, SIN, Name, Department, Address, Salary");
                    String input3 = scanner.nextLine();
                    Record r2 = employees.stringToRecord(input3);
                    employees.updateRecord(r1,r2);
                    break;

                default:
                System.out.println("GoodBye!");
                return;                    

            }
        }
        

        

    }

    public static void main(String[] args){
        runSystem();
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

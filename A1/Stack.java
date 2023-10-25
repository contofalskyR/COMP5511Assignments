//Based on information from the professor, we changed our code to insert EVERY name. 
//Every changed line has been commented out with "//WAS TOLD TO REMOVE

//Based on the current code, Ribeiro will be at the top of the stack at the end of the file

import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner;

public class Stack{
    Node top;

    //creates an empty stack
    public Stack(){
        top=null;
    }

    //adds a new node to the top of the stack
    public void push(String val){
        Node pushNode=new Node(val);
        if (top==null){
            top = pushNode;
        }
        else{
            pushNode.next=top;
            top=pushNode;
        }
    }

    //removes and returns top node, removing top node's connection 
    //and setting second node to top
    public Node pop(){
        Node returnNode;
        if (top==null){
            System.out.println("This stack is empty. Cannot pop.");
            return null;
        }else{
            returnNode = top;
            top = top.next;
            returnNode.next=null;
            return returnNode;
        }
    }

    //prints the stack from top to bottom
    public void printStack(){
        Node currNode = top;
        System.out.println();
        if(top==null){
            System.out.println("This stack is empty. Cannot print.");
        }else{
            while(currNode!=null){
                System.out.println(currNode.val);
                currNode=currNode.next;
            }
        }
    }


    public static void main(String[] args){
        //holds the value of the most recent command

        //String command = null;//WAS TOLD TO REMOVE

        String currentWord;
        Stack stack = new Stack();

        try{
            File file = new File("ds23s-a1.txt");
            Scanner reader = new Scanner(file);

            //continues until the end of the file
            while(reader.hasNext()){
                currentWord = reader.next();
                //System.out.println(currentWord);

                switch(currentWord.toLowerCase()){

                    //where currentWord is a command, sets current word, does command where necessary
                    case "insert": 
                    case "push":
                        break;
                    case "pop": 
                        //command="pop";//WAS TOLD TO REMOVE
                        stack.pop();
                        break;
                    case "print": //command="print";//WAS TOLD TO REMOVE
                        stack.printStack();
                        break;

                    //where currentWord is not a command, adds to list if most recent command is insert
                    default:
                    //if (command=="insert"){//WAS TOLD TO REMOVE
                        stack.push(currentWord);
                    //}//WAS TOLD TO REMOVE
                    break;
                        
                } 
                
            }
            System.out.println("\nAt the end of the file, "+stack.top.val+" is at the top of the stack.\n");
            reader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }

    }

}
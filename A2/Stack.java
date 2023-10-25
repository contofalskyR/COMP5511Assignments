class Node{
    String val;
    Node next;
    Node(String val){
        this.val = val;
        this.next = null;
    }
}

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

}
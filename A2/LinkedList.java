public class LinkedList {

    public static void main(String[] args) {
        //generates a Singly LinkedList object
        SinglyLinkedList linkedList = new SinglyLinkedList();
        // Add nodes to the linked list
        linkedList.generateList();
        // prints all nodes
        linkedList.printList();

        // counts the number of elements iteratively
        int iterativeCount = linkedList.countElementsIteratively();
        // counts the number of elements recursively
        int recursiveCount = linkedList.countElementsRecursively(linkedList.head);

        System.out.println("Iterative count: " + iterativeCount);
        System.out.println("Recursive count: " + recursiveCount);
    }

    static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    static class SinglyLinkedList {
        Node head;

        public SinglyLinkedList() {
            this.head = null;
        }

        public void generateList() {
            // starts the list from the value 5.
            head = new Node(5);
            Node tail = head;

            // begins from 10 and goes up to 50 in increments of 5.
            for (int i = 10; i <= 50; i += 5) {
                Node newNode = new Node(i);
                tail.next = newNode; // append the new node to the end of the list
                tail = newNode;      // move the tail to the new end
            }
        }


        public int countElementsIteratively() {
            int count = 0;
            Node currentNode = head;
            while (currentNode != null) {
                count++;
                currentNode = currentNode.next;
            }
            return count;
        }

        private int countElementsRecursively(Node node) {
            if (node == null) {
                return 0;
            }
            return 1 + countElementsRecursively(node.next);
        }

        public void printList() {
            // printing each node data
            Node currentNode = head;
            while (currentNode != null) {
                System.out.print(currentNode.data + " -> ");
                currentNode = currentNode.next;
            }
            System.out.println("null");
        }
    }
}



import java.util.List;
import java.util.Comparator;

// Including a Comparable interface
class BinaryTree<T extends Comparable<T>> {
    private Node<T> root; 

     public BinaryTree() {
        this.root = null;
    }

    // node construction 
     static class Node<T> {
        T key;
        Node<T> left, right;

        public Node(T key) {
            this.key = key;
            this.left = this.right = null;
        }
    }

     // add key to the binary tree 
     public void insert(T key) {
        root = insertRec(root, key);
    }

    

    private Node<T> insertRec(Node<T> root, T key) {
        // check if it is null 
        if (root == null) {
            root = new Node<>(key);
            return root;
        }

        //Inserting the new key into the tree and perform a recursive call to find the location 
        //finding the correct position in the tree for the new key 
        // if key is less than root key the method will call itself recursively for the left subtree 
        // if key is more than the root key the method will call itself recusively for the right subtree 
        if (key.compareTo(root.key) < 0) {
            root.left = insertRec(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            // 
            root.right = insertRec(root.right, key);
        }

        return root;
    }


    // In BinaryTree class
    public T search(T key) {
        Node<T> resultNode = searchRec(root, key);
        return resultNode == null ? null : resultNode.key;
    }

    private Node<T> searchRec(Node<T> root, T key) {
        if (root == null) {
        return null;
    }
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
        return searchRec(root.left, key);
    } else if (cmp > 0) {
        return searchRec(root.right, key);
    } else {
        return root; // found
    }
    }


    // public method 
     public void delete(T key) {
        root = deleteRec(root, key);
    }
    

    private Node<T> deleteRec(Node<T> root, T key) { 
        
        // base case 
        if (root == null) {
            return null;
        }

        // Find the node that we need to delete 
        // if the key is less that the current node key it means the node to delete is on the left side 
        // if the key is more that the current node key it means the node to delete is on the right side 

        if (key.compareTo(root.key) < 0) {
            root.left = deleteRec(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            root.right = deleteRec(root.right, key);
        } else {
            // Node with only one child or no child
            // if node to be deleted has no childre it means we are working with a leaf node 
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // node with two children so we get the inorder successor (smallest in the right subtree)
            root.key = minValue(root.right);

            // delete the inorder successor to maintain the BST properties 
            root.right = deleteRec(root.right, root.key);
        }

        return root;

        
    }

    // Finding the smallest key in the subtree needed to find the inorder successor during deletion 
    private T minValue(Node<T> root) {
        T minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    public static void updateRecord(List<Employee> employees, BinaryTree<Employee> tree, String employeeId, Employee updatedEmployee, Comparator<Employee> comparator) {
    // find the employee in the list and update details
    for (int i = 0; i < employees.size(); i++) {
        if (employees.get(i).getEmployeeId().equals(employeeId)) {
            // if the employee ID is being changed, update the binary tree
            if (!employeeId.equals(updatedEmployee.getEmployeeId())) {
                tree.delete(employees.get(i)); // remove old employee from the tree
                tree.insert(updatedEmployee); // add updated employee to the tree
            }

            employees.set(i, updatedEmployee); // update the list
            break;
        }
    }

    // re-sort the list if necessary
    HeapSort.sort(employees, comparator);
}


    // clearing the tree 
    public void clear() {
        root = null;
    }

    //public binary tree class 
    public void inOrderTraversal() {
        inOrderTraversal(this.root);
    }

    //display records
    private void inOrderTraversal(Node<T> node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println(node.key);
            inOrderTraversal(node.right);
        }
    }

}
public class BinaryTree {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        Node root = new Node('+');
        root.left = new Node('*');
        root.left.left = new Node('2');
        root.left.right = new Node('-');
        root.left.right.left = new Node('5');
        root.left.right.right = new Node('1');
        root.right = new Node('*');
        root.right.left = new Node('3');
        root.right.right = new Node('2');

        System.out.println(tree.evaluate(root));
    }
    static class Node {
        char value;
        Node left, right;

        Node(char item) {
            value = item;
            left = right = null;
        }
    }

    //  evaluate expression tree
    int evaluate(Node root) {
        if (root == null) return 0;

        // leaf nodes are numbers.
        if (root.left == null && root.right == null)
            return Character.getNumericValue(root.value);

        int leftVal = evaluate(root.left);
        int rightVal = evaluate(root.right);

        if (root.value == '+')
            return leftVal + rightVal;
        if (root.value == '-')
            return leftVal - rightVal;
        if (root.value == '*')
            return leftVal * rightVal;
        return leftVal / rightVal;
    }
}

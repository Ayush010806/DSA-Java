package Trees;

import java.util.Scanner;

class AVLTree {

    private static class Node {
        int value;
        int height;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
            this.height = 1;
        }
    }

    private Node root;

    // HEIGHT
    private int height(Node node) {
        if (node == null) {
            return 0;
        }

        return node.height;
    }

    // MAXIMUM OF TWO NUMBERS
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // GET BALANCE FACTOR
    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }

        return height(node.left) - height(node.right);
    }

    // RIGHT ROTATION
    private Node rightRotate(Node y) {

        Node x = y.left;
        Node temp = x.right;

        x.right = y;
        y.left = temp;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // LEFT ROTATION
    private Node leftRotate(Node x) {

        Node y = x.right;
        Node temp = y.left;

        y.left = x;
        x.right = temp;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // INSERT
    public void insert(int value) {
        root = insert(root, value);
    }

    private Node insert(Node node, int value) {

        // Normal BST insertion
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        }
        else if (value > node.value) {
            node.right = insert(node.right, value);
        }
        else {
            return node; // duplicate values not allowed
        }

        // Update height
        node.height = max(height(node.left), height(node.right)) + 1;

        // Get balance factor
        int balance = getBalance(node);

        // LL Case
        if (balance > 1 && value < node.left.value) {
            return rightRotate(node);
        }

        // RR Case
        if (balance < -1 && value > node.right.value) {
            return leftRotate(node);
        }

        // LR Case
        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL Case
        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // SEARCH
    public boolean search(int value) {
        return search(root, value);
    }

    private boolean search(Node node, int value) {

        if (node == null) {
            return false;
        }

        if (node.value == value) {
            return true;
        }

        if (value < node.value) {
            return search(node.left, value);
        }

        return search(node.right, value);
    }

    // MINIMUM
    public int min() {

        if (root == null) {
            return Integer.MAX_VALUE;
        }

        Node node = root;

        while (node.left != null) {
            node = node.left;
        }

        return node.value;
    }

    // MAXIMUM
    public int max() {

        if (root == null) {
            return Integer.MIN_VALUE;
        }

        Node node = root;

        while (node.right != null) {
            node = node.right;
        }

        return node.value;
    }

    // DELETE
    public void delete(int value) {
        root = delete(root, value);
    }

    private Node delete(Node node, int value) {

        if (node == null) {
            return null;
        }

        // Search for node
        if (value < node.value) {
            node.left = delete(node.left, value);
        }
        else if (value > node.value) {
            node.right = delete(node.right, value);
        }
        else {

            // No child
            if (node.left == null && node.right == null) {
                return null;
            }

            // Only right child
            if (node.left == null) {
                node = node.right;
            }

            // Only left child
            else if (node.right == null) {
                node = node.left;
            }

            // Two children
            else {
                Node temp = node.right;

                while (temp.left != null) {
                    temp = temp.left;
                }

                node.value = temp.value;
                node.right = delete(node.right, temp.value);
            }
        }

        // Update height
        node.height = max(height(node.left), height(node.right)) + 1;

        // Balance factor
        int balance = getBalance(node);

        // LL
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }

        // LR
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RR
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }

        // RL
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // INORDER
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node node) {

        if (node == null) {
            return;
        }

        inOrder(node.left);
        System.out.print(node.value + " ");
        inOrder(node.right);
    }

    // PREORDER
    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node node) {

        if (node == null) {
            return;
        }

        System.out.print(node.value + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    // POSTORDER
    public void postOrder() {
        postOrder(root);
        System.out.println();
    }

    private void postOrder(Node node) {

        if (node == null) {
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.value + " ");
    }

    // LEVEL ORDER USING ARRAY QUEUE
    public void levelOrder() {

        if (root == null) {
            return;
        }

        Node[] queue = new Node[100];

        int front = 0;
        int rear = 0;

        queue[rear++] = root;

        while (front < rear) {

            Node node = queue[front++];

            System.out.print(node.value + " ");

            if (node.left != null) {
                queue[rear++] = node.left;
            }

            if (node.right != null) {
                queue[rear++] = node.right;
            }
        }

        System.out.println();
    }

    // HEIGHT OF TREE
    public int getHeight() {
        return height(root);
    }

    // MAIN METHOD
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        AVLTree tree = new AVLTree();

        System.out.print("Enter number of nodes: ");
        int n = scanner.nextInt();

        System.out.println("Enter " + n + " values:");

        for (int i = 0; i < n; i++) {
            int value = scanner.nextInt();
            tree.insert(value);
        }

        System.out.println("\nInorder:");
        tree.inOrder();

        System.out.println("Preorder:");
        tree.preOrder();

        System.out.println("Postorder:");
        tree.postOrder();

        System.out.println("Level Order:");
        tree.levelOrder();

        System.out.println("Minimum: " + tree.min());
        System.out.println("Maximum: " + tree.max());
        System.out.println("Height: " + tree.getHeight());

        // SEARCH
        System.out.print("\nEnter value to search: ");
        int searchValue = scanner.nextInt();

        if (tree.search(searchValue)) {
            System.out.println(searchValue + " is present.");
        }
        else {
            System.out.println(searchValue + " is not present.");
        }

        // DELETE
        System.out.print("\nEnter value to delete: ");
        int deleteValue = scanner.nextInt();

        tree.delete(deleteValue);

        System.out.println("Inorder after deletion:");
        tree.inOrder();

        System.out.println("Level Order after deletion:");
        tree.levelOrder();

        scanner.close();
    }
}
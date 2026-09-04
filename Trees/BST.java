package Trees;

import java.util.Scanner;

class BST {

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    // INSERT
    public void insert(int value) {
        root = insert(root, value);
    }

    private Node insert(Node node, int value) {

        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } 
        else if (value > node.value) {
            node.right = insert(node.right, value);
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

    // DELETE
    public void delete(int value) {
        root = delete(root, value);
    }

    private Node delete(Node node, int value) {

        if (node == null) {
            return null;
        }

        if (value < node.value) {

            node.left = delete(node.left, value);

        } 
        else if (value > node.value) {

            node.right = delete(node.right, value);

        } 
        else {

            // Case 1: No child
            if (node.left == null && node.right == null) {
                return null;
            }

            // Case 2: Only right child
            if (node.left == null) {
                return node.right;
            }

            // Case 3: Only left child
            if (node.right == null) {
                return node.left;
            }

            // Case 4: Two children
            int minValue = findMin(node.right);

            node.value = minValue;

            node.right = delete(node.right, minValue);
        }

        return node;
    }

    private int findMin(Node node) {

        while (node.left != null) {
            node = node.left;
        }

        return node.value;
    }

    // VALID BST
    public boolean isValidBST() {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBST(Node node, long min, long max) {

        if (node == null) {
            return true;
        }

        if (node.value <= min || node.value >= max) {
            return false;
        }

        return isValidBST(node.left, min, node.value)
                && isValidBST(node.right, node.value, max);
    }

    // MAIN METHOD
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        BST tree = new BST();

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

        System.out.print("\nEnter value to search: ");
        int searchValue = scanner.nextInt();

        if (tree.search(searchValue)) {
            System.out.println(searchValue + " is present.");
        } else {
            System.out.println(searchValue + " is not present.");
        }

        System.out.print("\nEnter value to delete: ");
        int deleteValue = scanner.nextInt();
        tree.delete(deleteValue);

        System.out.println("Inorder after deletion:");
        tree.inOrder();

        System.out.println("Is Valid BST: " + tree.isValidBST());

        scanner.close();
    }
}
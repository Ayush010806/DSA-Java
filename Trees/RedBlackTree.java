package Trees;

import java.util.Scanner;

class RedBlackTree {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        int value;
        boolean color;
        Node left;
        Node right;
        Node parent;

        Node(int value) {
            this.value = value;
            this.color = RED;
        }
    }

    private Node root;

    // LEFT ROTATION
    private void leftRotate(Node x) {

        Node y = x.right;

        x.right = y.left;

        if (y.left != null) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        }
        else if (x == x.parent.left) {
            x.parent.left = y;
        }
        else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    // RIGHT ROTATION
    private void rightRotate(Node y) {

        Node x = y.left;

        y.left = x.right;

        if (x.right != null) {
            x.right.parent = y;
        }

        x.parent = y.parent;

        if (y.parent == null) {
            root = x;
        }
        else if (y == y.parent.left) {
            y.parent.left = x;
        }
        else {
            y.parent.right = x;
        }

        x.right = y;
        y.parent = x;
    }

    // INSERT
    public void insert(int value) {

        Node newNode = new Node(value);

        if (root == null) {
            newNode.color = BLACK;
            root = newNode;
            return;
        }

        Node current = root;
        Node parent = null;

        while (current != null) {

            parent = current;

            if (value < current.value) {
                current = current.left;
            }
            else if (value > current.value) {
                current = current.right;
            }
            else {
                return; // duplicate not allowed
            }
        }

        newNode.parent = parent;

        if (value < parent.value) {
            parent.left = newNode;
        }
        else {
            parent.right = newNode;
        }

        fixInsert(newNode);
    }

    // FIX INSERT
    private void fixInsert(Node node) {

        while (node != root && node.parent.color == RED) {

            Node parent = node.parent;
            Node grandParent = parent.parent;

            // Parent is left child
            if (parent == grandParent.left) {

                Node uncle = grandParent.right;

                // Case 1: Uncle is RED
                if (uncle != null && uncle.color == RED) {

                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandParent.color = RED;

                    node = grandParent;
                }

                else {

                    // Case 2: RL inside left side
                    if (node == parent.right) {
                        node = parent;
                        leftRotate(node);
                        parent = node.parent;
                    }

                    // Case 3: LL
                    parent.color = BLACK;
                    grandParent.color = RED;

                    rightRotate(grandParent);
                }
            }

            // Parent is right child
            else {

                Node uncle = grandParent.left;

                // Case 1: Uncle is RED
                if (uncle != null && uncle.color == RED) {

                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandParent.color = RED;

                    node = grandParent;
                }

                else {

                    // Case 2: LR inside right side
                    if (node == parent.left) {
                        node = parent;
                        rightRotate(node);
                        parent = node.parent;
                    }

                    // Case 3: RR
                    parent.color = BLACK;
                    grandParent.color = RED;

                    leftRotate(grandParent);
                }
            }
        }

        root.color = BLACK;
    }

    // SEARCH
    public boolean search(int value) {

        Node current = root;

        while (current != null) {

            if (value == current.value) {
                return true;
            }

            if (value < current.value) {
                current = current.left;
            }
            else {
                current = current.right;
            }
        }

        return false;
    }

    // MINIMUM
    public int min() {

        if (root == null) {
            return Integer.MAX_VALUE;
        }

        Node current = root;

        while (current.left != null) {
            current = current.left;
        }

        return current.value;
    }

    // MAXIMUM
    public int max() {

        if (root == null) {
            return Integer.MIN_VALUE;
        }

        Node current = root;

        while (current.right != null) {
            current = current.right;
        }

        return current.value;
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

        System.out.print(node.value);

        if (node.color == RED) {
            System.out.print("(R) ");
        }
        else {
            System.out.print("(B) ");
        }

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

        System.out.print(node.value);

        if (node.color == RED) {
            System.out.print("(R) ");
        }
        else {
            System.out.print("(B) ");
        }

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

        System.out.print(node.value);

        if (node.color == RED) {
            System.out.print("(R) ");
        }
        else {
            System.out.print("(B) ");
        }
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

            System.out.print(node.value);

            if (node.color == RED) {
                System.out.print("(R) ");
            }
            else {
                System.out.print("(B) ");
            }

            if (node.left != null) {
                queue[rear++] = node.left;
            }

            if (node.right != null) {
                queue[rear++] = node.right;
            }
        }

        System.out.println();
    }

    // MAIN METHOD
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        RedBlackTree tree = new RedBlackTree();

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
        }
        else {
            System.out.println(searchValue + " is not present.");
        }

        scanner.close();
    }
}
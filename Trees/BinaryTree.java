package Trees;
import java.util.Scanner;

class BinaryTree {

  public BinaryTree() {

  }

  private static class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
      this.value = value;
    }
  }

  private Node root;

  // insert elements
  public void populate(Scanner scanner) {
    System.out.println("Enter the root Node: ");
    int value = scanner.nextInt();
    root = new Node(value);
    populate(scanner, root);
  }

  private void populate(Scanner scanner, Node node) {

    System.out.println("Do you want to enter left of " + node.value);
    boolean left = scanner.nextBoolean();

    if (left) {
      System.out.println("Enter the value of the left of " + node.value);
      int value = scanner.nextInt();
      node.left = new Node(value);
      populate(scanner, node.left);
    }

    System.out.println("Do you want to enter right of " + node.value);
    boolean right = scanner.nextBoolean();

    if (right) {
      System.out.println("Enter the value of the right of " + node.value);
      int value = scanner.nextInt();
      node.right = new Node(value);
      populate(scanner, node.right);
    }
  }

  // display tree
  public void display() {
    display(this.root, "");
  }

  private void display(Node node, String indent) {

    if (node == null) {
      return;
    }

    System.out.println(indent + node.value);

    display(node.left, indent + "\t");
    display(node.right, indent + "\t");
  }

  // pretty display
  public void prettyDisplay() {
    prettyDisplay(root, 0);
  }

  private void prettyDisplay(Node node, int level) {

    if (node == null) {
      return;
    }

    prettyDisplay(node.right, level + 1);

    if (level != 0) {
      for (int i = 0; i < level - 1; i++) {
        System.out.print("|\t\t");
      }

      System.out.println("|------->" + node.value);

    } else {
      System.out.println(node.value);
    }

    prettyDisplay(node.left, level + 1);
  }

  // =========================
  // TREE TRAVERSALS
  // =========================

  // Preorder
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

  // Inorder
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

  // Postorder
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

  // Level Order
  public void levelOrder() {

    if (root == null) {
      return;
    }

    Scanner scanner = new Scanner(System.in);

    // Using array-like queue
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

  // SEARCH NODE

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

    return search(node.left, value) ||
           search(node.right, value);
  }

  // MINIMUM ELEMENT

  public int min() {
    return min(root);
  }

  private int min(Node node) {

    if (node == null) {
      return Integer.MAX_VALUE;
    }

    int min = node.value;

    int leftMin = min(node.left);
    int rightMin = min(node.right);

    if (leftMin < min) {
      min = leftMin;
    }

    if (rightMin < min) {
      min = rightMin;
    }

    return min;
  }

  // MAXIMUM ELEMENT

  public int max() {
    return max(root);
  }

  private int max(Node node) {

    if (node == null) {
      return Integer.MIN_VALUE;
    }

    int max = node.value;

    int leftMax = max(node.left);
    int rightMax = max(node.right);

    if (leftMax > max) {
      max = leftMax;
    }

    if (rightMax > max) {
      max = rightMax;
    }

    return max;
  }

  // INVERT BINARY TREE

      public void invert() {
        invert(root);
    }

    private void invert(Node node) {
        if (node == null) {
            return;
        }

        Node temp = node.left;
        node.left = node.right;
        node.right = temp;

        invert(node.left);
        invert(node.right);
    }

    // MAIN METHOD
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BinaryTree tree = new BinaryTree();

        tree.populate(scanner);

        System.out.println("Tree:");
        tree.prettyDisplay();

        System.out.println("Preorder:");
        tree.preOrder();

        System.out.println("Inorder:");
        tree.inOrder();

        System.out.println("Postorder:");
        tree.postOrder();

        System.out.println("Level Order:");
        tree.levelOrder();

        System.out.println("Minimum: " + tree.min());
        System.out.println("Maximum: " + tree.max());

        System.out.print("Enter value to search: ");
        int value = scanner.nextInt();

        if (tree.search(value)) {
        System.out.println(value + " is present in the tree.");
        } else {
        System.out.println(value + " is not present in the tree.");
        }

        System.out.println("Inverting tree...");
        tree.invert();

        System.out.println("Level After Invert:");
        tree.levelOrder();
    }
}

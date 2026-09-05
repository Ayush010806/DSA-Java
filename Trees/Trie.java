package Trees;

import java.util.Scanner;

class Trie {

    // NODE
    private static class Node {

        Node[] children = new Node[26];

        boolean isEnd;
    }

    private Node root;

    // CONSTRUCTOR
    public Trie() {
        root = new Node();
    }

    // INSERT
    public void insert(String word) {

        Node current = root;

        for (int i = 0; i < word.length(); i++) {

            char ch = word.charAt(i);

            int index = ch - 'a';

            if (current.children[index] == null) {
                current.children[index] = new Node();
            }

            current = current.children[index];
        }

        current.isEnd = true;
    }

    // SEARCH
    public boolean search(String word) {

        Node current = root;

        for (int i = 0; i < word.length(); i++) {

            char ch = word.charAt(i);

            int index = ch - 'a';

            if (current.children[index] == null) {
                return false;
            }

            current = current.children[index];
        }

        return current.isEnd;
    }

    // STARTS WITH
    public boolean startsWith(String prefix) {

        Node current = root;

        for (int i = 0; i < prefix.length(); i++) {

            char ch = prefix.charAt(i);

            int index = ch - 'a';

            if (current.children[index] == null) {
                return false;
            }

            current = current.children[index];
        }

        return true;
    }

    // DELETE 
    public void delete(String word) {

        delete(root, word, 0);
    }

    private boolean delete(Node current, String word, int index) {

        // Word not found
        if (index == word.length()) {

            if (!current.isEnd) {
                return false;
            }

            current.isEnd = false;

            return hasNoChildren(current);
        }

        char ch = word.charAt(index);

        int childIndex = ch - 'a';

        Node child = current.children[childIndex];

        if (child == null) {
            return false;
        }

        boolean shouldDeleteChild =
                delete(child, word, index + 1);

        if (shouldDeleteChild) {
            current.children[childIndex] = null;
        }

        return !current.isEnd && hasNoChildren(current);
    }

    // CHECK WHETHER NODE HAS CHILDREN
    private boolean hasNoChildren(Node node) {

        for (int i = 0; i < 26; i++) {

            if (node.children[i] != null) {
                return false;
            }
        }

        return true;
    }

    // DISPLAY ALL WORDS
    public void display() {

        display(root, "");
    }

    private void display(Node node, String word) {

        if (node.isEnd) {
            System.out.println(word);
        }

        for (int i = 0; i < 26; i++) {

            if (node.children[i] != null) {

                char ch = (char) ('a' + i);

                display(node.children[i], word + ch);
            }
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Trie trie = new Trie();

        System.out.print("Enter number of words: ");
        int n = scanner.nextInt();

        System.out.println("Enter " + n + " words:");

        for (int i = 0; i < n; i++) {

            String word = scanner.next().toLowerCase();

            trie.insert(word);
        }

        System.out.println("\nWords in Trie:");

        trie.display();

        // WORD TO SEARCH 
        System.out.print("\nEnter word to search: ");
        String searchWord = scanner.next().toLowerCase();

        if (trie.search(searchWord)) {
            System.out.println(searchWord + " is present.");
        }
        else {
            System.out.println(searchWord + " is not present.");
        }

        // PREFIX SEARCH
        System.out.print("\nEnter prefix to search: ");
        String prefix = scanner.next().toLowerCase();

        if (trie.startsWith(prefix)) {
            System.out.println("Words starting with " + prefix + " exist.");
        }
        else {
            System.out.println("No word starts with " + prefix + ".");
        }

        // DELETE THE WORD
        System.out.print("\nEnter word to delete: ");
        String deleteWord = scanner.next().toLowerCase();

        trie.delete(deleteWord);

        System.out.println("\nWords after deletion:");

        trie.display();

        scanner.close();
    }
}
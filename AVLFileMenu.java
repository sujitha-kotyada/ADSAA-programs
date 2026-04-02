import java.io.*;
import java.util.*;

class Node {
    int data, height;
    Node left, right;

    Node(int value) {
        data = value;
        height = 0;
        left = right = null;
    }
}

class AVLTree {
    private Node root;

    int getHeight(Node n) {
        return (n == null) ? -1 : n.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    Node insert(Node root, int value) {
        if (root == null)
            root = new Node(value);
        else if (value < root.data) {
            root.left = insert(root.left, value);
            if (getHeight(root.left) - getHeight(root.right) == 2) {
                if (value < root.left.data)
                    root = rotateWithLeftChild(root);
                else
                    root = doubleWithLeftChild(root);
            }
        } else if (value > root.data) {
            root.right = insert(root.right, value);
            if (getHeight(root.right) - getHeight(root.left) == 2) {
                if (value > root.right.data)
                    root = rotateWithRightChild(root);
                else
                    root = doubleWithRightChild(root);
            }
        }

        root.height = max(getHeight(root.left), getHeight(root.right)) + 1;
        return root;
    }

    Node delete(Node root, int key) {
        if (root == null)
            return null;

        if (key < root.data)
            root.left = delete(root.left, key);
        else if (key > root.data)
            root.right = delete(root.right, key);
        else {
            if (root.left != null && root.right != null) {
                Node min = findMin(root.right);
                root.data = min.data;
                root.right = delete(root.right, min.data);
            } else {
                root = (root.left != null) ? root.left : root.right;
            }
        }

        if (root != null) {
            root.height = max(getHeight(root.left), getHeight(root.right)) + 1;

            if (getHeight(root.left) - getHeight(root.right) == 2) {
                if (getHeight(root.left.left) >= getHeight(root.left.right))
                    root = rotateWithLeftChild(root);
                else
                    root = doubleWithLeftChild(root);
            } else if (getHeight(root.right) - getHeight(root.left) == 2) {
                if (getHeight(root.right.right) >= getHeight(root.right.left))
                    root = rotateWithRightChild(root);
                else
                    root = doubleWithRightChild(root);
            }
        }
        return root;
    }

    Node findMin(Node root) {
        if (root == null || root.left == null)
            return root;
        return findMin(root.left);
    }

    Node rotateWithLeftChild(Node k2) {
        Node k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max(getHeight(k2.left), getHeight(k2.right)) + 1;
        k1.height = max(getHeight(k1.left), k2.height) + 1;
        return k1;
    }

    Node rotateWithRightChild(Node k1) {
        Node k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max(getHeight(k1.left), getHeight(k1.right)) + 1;
        k2.height = max(getHeight(k2.right), k1.height) + 1;
        return k2;
    }

    Node doubleWithLeftChild(Node k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    Node doubleWithRightChild(Node k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }
    }

    // Wrapper methods
    public void insertValue(int value) {
        root = insert(root, value);
    }

    public void deleteValue(int value) {
        root = delete(root, value);
    }

    public void display() {
        System.out.print("Inorder Traversal: ");
        inorder(root);
        System.out.println();
    }
}

public class AVLFileMenu {
    public static void main(String[] args) throws Exception {
        AVLTree tree = new AVLTree();
        Scanner sc = new Scanner(System.in);

        // Construct AVL Tree from file
        File file = new File("input.txt");
        Scanner fileReader = new Scanner(file);

        while (fileReader.hasNextInt()) {
            tree.insertValue(fileReader.nextInt());
        }

        int choice;
        do {
            System.out.println("1. Display");
            System.out.println("2. Insert");
            System.out.println("3. Delete");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    tree.display();
                    break;

                case 2:
                    System.out.print("Enter element to insert: ");
                    int ins = sc.nextInt();
                    tree.insertValue(ins);
                    break;

                case 3:
                    System.out.print("Enter element to delete: ");
                    int del = sc.nextInt();
                    tree.deleteValue(del);
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 4);
    }
}
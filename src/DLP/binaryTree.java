package DLP;

/**
 * Simplified BinaryTree Data Structure.
 * Assumes no duplicate values. No balancing.
 * @author Jinqiu Liu
 */
public class binaryTree {
    class Node {
        int index;
        long value;
        Node left;
        Node right;
        public Node(int i, long v) {
            index = i;
            value = v;
            left = null;
            right = null;
        }
    }
    Node root;
    public binaryTree() {
        root = null;
    }
    
    public void add(int i, long v) {
        if (root == null) {
            root = new Node(i,v);
        } else {
            add(root, i, v);
        }
    }
    public void add(Node n, int i, long v) {        
        if (n == null) {
            n = new Node(i,v);
        } else {
            if (n.value > v) {
                //new node goes as left child
                if (n.left == null) {
                    //ok to add
                    n.left = new Node(i,v);
                } else {
                    //go to subtree
                    add(n.left, i, v);
                }
            } else {
                //new node goes as right child
                if (n.right == null) {
                    //ok to add
                    n.right = new Node(i,v);
                } else {
                    //go to subtree
                    add(n.right, i, v);
                }
            }
        }
    }
    
    public int find(long v) {
        return find(root, v);
    }
    
    public int find(Node n, long v) {
        if (n == null) {
            return -1;
        }
        if (n.value == v) {
            return n.index;
        } else {
            if (n.value > v) {
                return find (n.left, v);
            } else {
                return find (n.right, v);
            }
        }
    }
}

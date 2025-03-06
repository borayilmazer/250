public class AVLTree {

    private static class TreeNode {
        int value;
        int height;
        TreeNode left;
        TreeNode right;

        // Constructors
        TreeNode(int value) {
            this(value, null, null);
        }
        TreeNode(int value, TreeNode left, TreeNode right){
            this.value = value;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }
    private static final int ALLOWED_IMBALANCE = 1;
    private TreeNode root;
    private int size;
    AVLTree(){
        TreeNode root = null;
        int size = 0;

    }

    private int height(TreeNode t){
        if (t == null){
            return -1;
        }
        else {
            return t.height;
        }
    }
    // standard insertion method with the addition of a balance method
    public void insert(int x) {
        root = insert(x, root); // Start inserting from the root
        size++; // Update the size (duplİCte problematic)
    }
    public TreeNode insert(int x, TreeNode t){
        if (t == null){
            return new TreeNode(x,null,null);
        }
        if (x < t.value){
            t.left = insert(x , t.left);
        }
        else if (x > t.value){
            t.right = insert(x, t.right);
        }
        else
            ;
        return balance(t);
    }
    // balance method - the gardener that keeps this tree well maintained :-)
    private TreeNode balance(TreeNode t){
        if (t == null){
            return t;
        }
        // check the height imbalance (and the direction of said imbalance) of the subtree, perform rotations if necessary
        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
            if (height(t.left.left) >= height(t.left.right)) {
                t = rotateWithLeftChild(t);
            } else {
                t = doubleRotateWithLeftChild(t);
            }
        }
        // check the height imbalance of the subtree, perform rotations if necessary
        else if ( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE ){
            if( height( t.right.right ) >= height( t.right.left ) ){
                t = rotateWithRightChild(t);
            }
            else {
                t = doubleRotateWithRightChild(t);
            }
        }
        t.height = Math.max(height(t.left),height(t.right)) + 1;
        return t;
    }
    // rotation methods, right and left rotations are written symmetrically
    private TreeNode rotateWithLeftChild( TreeNode t){
        TreeNode tLeftChild = t.left;
        t.left = tLeftChild.right;
        tLeftChild.right = t;
        // update the heights of the rotated nodes
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        tLeftChild.height = Math.max(height(tLeftChild.left), t.height ) + 1;
        return tLeftChild;
    }
    private TreeNode rotateWithRightChild( TreeNode t){
        TreeNode tRightChild = t.right;
        t.right = tRightChild.left;
        tRightChild.left = t;
        // update the heights of the rotated nodes
        t.height = Math.max(height(t.right), height(t.left)) + 1;
        tRightChild.height = Math.max(height(tRightChild.right), t.height) + 1;
        return tRightChild;
    }
    private TreeNode doubleRotateWithLeftChild(TreeNode t){
        t.left = rotateWithRightChild(t.left);
        return rotateWithLeftChild(t);
    }
    private TreeNode doubleRotateWithRightChild(TreeNode t){
        t.right = rotateWithLeftChild(t.right);
        return rotateWithRightChild(t);
    }
    // removal method along with its wrapper
    public void remove(int x) {
        root = remove(x,root);
        size--;
    }
    private TreeNode remove(int x, TreeNode t){
        if (t == null){
            return t;
        }
        if (x < t.value){
            t.left = remove(x,t.left);
        }
        else if (x > t.value){
            t.right = remove(x,t.right);
        }
        else if (t.left != null && t.right != null){
            t.value = findMin( t.right ).value;
            t.right = remove(t.value, t.right);
        }
        else{
            if (t.left != null){
                t = t.left;
            }
            else{
                t = t.right;
            }
        }
        return balance(t);
    }

    // Search method along with its wrapper
    public boolean search(int x){
        return recursiveSearch(x, root);
    }
    private boolean recursiveSearch(int x, TreeNode t){
        if (t == null){
            return false;
        }
        if (t.value == x){
            return true;
        }
        if (x < t.value){
            return recursiveSearch(x, t.left);
        }
        else if (x > t.value){
            return recursiveSearch(x, t.right);
        }
        return false;
    }


    // returns the smallest element of a binary tree
    public TreeNode findMin(TreeNode t){
        if (t==null){
            return null;
        }
        else if(t.left == null){
            return t;
        }
        return findMin(t.left);
    }
    // given an integer, returns the largest integer value within the nodes of a tree that is still smaller than the input integer
    public Integer findLargestSmallerThan(int value) {
        TreeNode current = this.root;  // start from the root of the AVL tree
        Integer result = -1;
        if (value == 0 || value == 1){
            return -1;
        }

        while (current != null) {
            if (current.value < value) {
                result = current.value;  // update result since current node is a candidate
                current = current.right;  // move right to find a larger candidate that is still smaller than `value`
            } else {
                current = current.left;  // move left to find smaller nodes
            }
        }

        return result;  // returns -1 if no suitable element exists
    }
    // symmetric version of findLargestSmallerThan
    public Integer findSmallestLargerThan(int value) {
        TreeNode current = this.root;  // Start from the root of the AVL tree
        Integer result = -1;

        while (current != null) {
            if (current.value > value) {
                result = current.value;  // update result since current node is a candidate
                current = current.left;  // move left to find a smaller candidate that is still larger than `value`
            } else {
                current = current.right;  // move right to find larger nodes
            }
        }
        return result;  // returns -1 if no suitable element exists
    }
}

import java.lang.Math;
/*
 * RefBasedBinarySearchTree.java
 *
 * A ref-based BinaryTree meant to store values of type Integer
 */
public class RefBasedBinarySearchTree extends RefBasedBinaryTree {

    @Override
    public void insert(Integer value) {
        if (this.root == null) {
            this.root = new TreeNode(value);
        }
        else {
            TreeNode newNode = new TreeNode(value);
            TreeNode curNode = this.root;
            while (curNode != null) {
                if (value < curNode.data) {
                    if (curNode.getLeft() == null) {
                        curNode.setLeft(newNode);
                        break;
                    }
                    else {
                        curNode = curNode.getLeft();
                    }
                }
                else {
                    if (curNode.getRight() == null) {
                        curNode.setRight(newNode);
                        break;
                    }
                    else {
                        curNode = curNode.getRight();
                    }
                }
            }
            curNode = newNode;
        }
    }

        @Override
        public int getMax(){
            TreeNode cur = root;
            if (cur == null) {
                return -1;
            }
            while (cur.getRight() != null) {
                cur = cur.getRight();
            }
            return cur.data;
        }
        @Override
        public boolean find(int val){
            TreeNode cur = root;
            while (cur != null) {
                if (cur.data == val) {
                    return true;
                }
                else if (cur.data > val) {
                    cur = cur.getLeft();
                }
                else if (cur.data < val) {
                    cur = cur.getRight();
                }
            }
            return false;
        }
    public static void main(String[] args) {
        // use these trees to test the methods you will implement
        RefBasedBinarySearchTree emptyTree = new RefBasedBinarySearchTree();
        RefBasedBinarySearchTree myTree = new RefBasedBinarySearchTree();
        myTree.insert(2);
        myTree.insert(1);
        myTree.insert(5);
        myTree.insert(7);
        myTree.insert(0);
        myTree.insert(4);
        myTree.insert(6);
        
        System.out.println("in");
        myTree.inOrder();
        System.out.println("pre");
        myTree.preOrder();
        System.out.println("post");
        myTree.postOrder();
        
        System.out.println("toString\n" + myTree);

        System.out.println(myTree);
        System.out.println(myTree.sum());
        System.out.println(myTree.find(5));
        System.out.println(myTree.find(10));
        System.out.println(myTree.getMax());
        System.out.println(emptyTree.getMax());
    }
}

public class ArrayBasedBinarySearchTree extends ArrayBasedBinaryTree{

    @Override
    public void insert(Integer value) {
        if (this.data.length == 0) {
            this.root = value;
            data[0] = value;
            this.size ++;
        }
        else {
            int curPos = 0;
            while (curPos < this.data.length) {
                if (data[curPos] == null) {
                    data[curPos] = value;
                    this.size ++; 
                    break;
                }

                curPos = (value < this.data[curPos])? getLeft(curPos) : getRight(curPos);
            }
        }
    }
    
    public static void main(String[] args) {
        ArrayBasedBinarySearchTree emptyTree = new ArrayBasedBinarySearchTree();
        
        ArrayBasedBinarySearchTree myTree = new ArrayBasedBinarySearchTree();
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
    }

}

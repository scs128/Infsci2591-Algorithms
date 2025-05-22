import javax.swing.tree.TreeNode;

public class ThreeTwoTree {
    private int size;
    public BTreeNode root;

    public ThreeTwoTree(){
        size = 0;
        root = null;
    }


    public void putKey(int key){
        if(root == null){
            root = new BTreeNode();
        }

        BTreeNode [] arr = root.put(key);

        if(arr[1] == null){
            root = arr[0];
        }else{
            root = new BTreeNode();
            root.children[0] = arr[0];
            root.children[1] = arr[1];
            root.keys[0] = arr[0].keys[1];
            root.children[0].keys[1] = -1;
            root.numChildren = 2;
            root.numKeys = 1;
        }

        size++;
        //System.out.println(root.keys[0]);
        //System.out.println(root.keys[1]);
    }

    public String toString(){
        return root.toString();
    }


    
}

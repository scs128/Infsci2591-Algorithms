import java.util.ArrayList;

public class BTreeNode {
    int [] keys; 
    BTreeNode [] children;
    int numKeys;
    int numChildren;


    public BTreeNode(){
        keys = new int[2]; //might need to initialize to 3 for temporary holding of keys (unsure right now)
        children = new BTreeNode[3]; //allow an extra space specifically for the case in which we are splitting up the tree
        numKeys = 0;
    }

    public String toString(){
        String result = "";
        if(checkLeaf()){
            for(int i = 0; i < numKeys; i++){
                result = result + " " + keys[i];
            }
        }else{
            int i = 0;
            for(; i < numKeys; i++){
                if(children[i] != null){
                    result = result + children[i].toString();
                }
                result = result + " " + keys[i];
            }
            if(children[i] != null){
                result = result + children[i].toString();
            }
        }
        //System.out.println(result);
        return result;
    }

    public boolean checkLeaf(){
        return (numChildren == 0);
    }

    public BTreeNode[] put(int key){
        BTreeNode [] result = new BTreeNode[2];
        BTreeNode [] arr = new BTreeNode[2];
        int i; //index of child position
        for(i = 0; i < numKeys && key > keys[i]; i++){}
        

        if(!checkLeaf()){
            if(children[i] == null){
                children[i] = new BTreeNode();
            }
            arr = children[i].put(key);
            if(arr[1] == null){ //insertion was successful at lower level, return result where [0] = node, [1] = null 
                result[0] = this;
                return result;
            }
        }

        
        //attempt to insert at this level, split if necessary
        // need to split
        if(numKeys == 2){
            //System.out.println(keys[0]);
            result[0] = new BTreeNode();
            result[1] = new BTreeNode();
            if(i == 0){
                if(!checkLeaf()){
                    result[0].keys[0] = arr[0].keys[1];
                    arr[0].keys[1] = -1;

                    result[0].children[0] = arr[0];
                    result[0].children[1] = arr[1];
                    result[1].children[0] = children[1];
                    result[1].children[1] = children[2];
                }else{
                    result[0].keys[0] = key;
                }
                result[0].keys[1] = keys[0];
                result[1].keys[0] = keys[1];
            }else if(i == 1){
                if(!checkLeaf()){ 
                    result[0].keys[1] = arr[0].keys[1];
                    arr[0].keys[1] = -1;

                    result[0].children[0] = children[0];
                    result[0].children[1] = arr[0];
                    result[1].children[0] = arr[1];
                    result[1].children[1] = children[2];
                    
                }else{
                    result[0].keys[1] = key;
                }
                result[0].keys[0] = keys[0];
                result[1].keys[0] = keys[1];
            }else{
                if(!checkLeaf()){
                    result[1].keys[0] = arr[0].keys[1];
                    arr[0].keys[1] = -1;

                    result[0].children[0] = children[0];
                    result[0].children[1] = children[1];
                    result[1].children[0] = arr[0];
                    result[1].children[1] = arr[1];
                    
                }else{
                    result[1].keys[0] = key;
                }
                result[0].keys[0] = keys[0];
                result[0].keys[1] = keys[1];
            }
            result[0].numKeys = 1;
            result[1].numKeys = 1;
            result[0].numChildren = result[0].numChildren();
            result[1].numChildren = result[1].numChildren();

            return result;
        }else{ // do not need to split
            //System.out.println("Do not need to split");
            if(numKeys == 0){
                keys[0] = key;
            }else if(key < keys[0]){
                //System.out.println(keys[0]);
                keys[1] = keys[0];
                if(arr[0] != null){
                    keys[0] = arr[0].keys[1];
                }else{
                    keys[0] = key;
                }
                children[2] = children[1];
                children[1] = arr[1];
                children[0] = arr[0];
            }else{
                if(arr[0] != null){
                    keys[1] = arr[0].keys[1];
                }else{
                    keys[1] = key;
                }
                children[1] = arr[0];
                children[2] = arr[1];
            }
            numKeys++;
            result[0] = this;
                
        }
        return result;
    }

    public int numChildren(){
        int count = 0;
        for(BTreeNode n : children){
            if(n != null){
                count++;
            }
        }
        return count;
    }
}

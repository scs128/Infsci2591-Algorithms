public class TreeTest {
    public static void main(String[]args){
        int [] keys = {1, 2, 3, 4, 5, 6, 7, 8, 9}; // , 7, 2, 4, 8, 5
        ThreeTwoTree tree = new ThreeTwoTree();

        for(int val : keys){
            tree.putKey(val);
        }

        System.out.println(tree.toString());
        System.out.println(tree.root.keys[1]);
        System.out.println(tree.root.children[1].children[2].toString());

    }
}

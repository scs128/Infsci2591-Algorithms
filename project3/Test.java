import java.io.File;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Test {
    public static void main(String[]args){
        File folder = new File("./Project3-Input-Files");
        File [] listFiles = folder.listFiles();

        Graph [] graphs = new Graph[listFiles.length];
        for(int i = 0; i < listFiles.length; i++){
            
            if(listFiles[i].getName().contains("csv")){
                String name = listFiles[i].getName();
                graphs[i] = new Graph("./Project3-Input-Files/" + name);
                System.out.println("---------- " + name + " ----------");
                System.out.println("Nearest Neighbor");
                nearestNeighbor(0, graphs[i].size(), graphs[i], new int[graphs[i].size()]);
                System.out.println("\nNearest Insertion");
                nearestInsertion(graphs[i].size(), graphs[i], new int[graphs[i].size()]);
                System.out.println("\nDynamic Programming");
                dynamicTravel(graphs[i].size(), graphs[i]);
                System.out.println("\nBranch-and-Bound");
                branchTravel(graphs[i].size(), graphs[i]);
            }
        }
    }

    public static void nearestNeighbor(int start, int n, Graph g, int [] tour){
        tour[0] = start; // first node is 0
        boolean [] visited = new boolean[g.size()];
        visited [start] = true;
        for(int i = 1; i < n; i++){
            for(int j = 0; j < n; j++){
                if(!visited[j]){
                    if(tour[i] == 0 && visited[0]){
                        tour[i] = j;
                    }else if(g.get(tour[i-1], tour[i]) > g.get(tour[i-1], j)){
                        tour[i] = j;
                    }
                }
            }
            visited[tour[i]] = true;
        }

        double length = 0;
        int i = 0;
        System.out.print("Path: ");
        for(; i < n; i++){
            System.out.print(tour[i] + ", ");
            if(i > 0){
                length = length + g.get(tour[i-1], tour[i]);
            }
        }
        length = length + g.get(tour[i-1], 0);
        System.out.println(start + " Length: " + length);
    }

    public static void nearestInsertion(int n, Graph g, int [] tour){
        //find shortest link
        int shortest1 = 0;
        int shortest2 = 1;
        for(int i = 0; i < g.size()-1; i++){
            for(int j = i + 1; j < g.size(); j++){
                if(g.get(i, j) != 0 && g.get(i, j) < g.get(shortest1, shortest2)){
                    shortest1 = i;
                    shortest2 = j;
                }
            }
        }

        tour[0] = shortest1; // first node is 0
        tour[1] = shortest2;
        boolean [] visited = new boolean[g.size()];
        visited[shortest1] = true;
        visited[shortest2] = true;
        for(int i = 2; i < n; i++){
            for(int j = 0; j < n; j++){
                if(!visited[j]){
                    if(tour[i] == 0 && visited[0]){
                        tour[i] = j;
                    }else if(g.get(tour[i-1], tour[i]) > g.get(tour[i-1], j)){
                        tour[i] = j;
                    }
                }
            }
            visited[tour[i]] = true;
        }

        double length = 0;
        int i = 0;
        System.out.print("Path: ");
        for(; i < n; i++){
            System.out.print(tour[i] + ", ");
            if(i > 0){
                length = length + g.get(tour[i-1], tour[i]);
            }
        }
        length = length + g.get(tour[i-1], 0);
        System.out.println(shortest1 + " Length: " + length);
    }

    public static void dynamicTravel(int n, Graph W){
        int [][] P = new int[n][n];
        double minlength;
        //treat D as a function with input node index and 
        int [] subset = new int[n-1];
        for(int i = 1; i < n; i++){
            subset[i-1] = i;
        }

        HashMap<String, Double> d = new HashMap<>();
        HashMap<String, String> p = new HashMap<>();
        String key = D(0, subset, W, d, p);
        System.out.println("Path: " + p.get(key) + " Length: " + d.get(key));
    }

    // Returns the minlength of a tour from index to 0 using the indices in exclude that are false
    public static String D(int index, int [] subset, Graph g, HashMap<String,Double> d, HashMap<String, String> p){
        double min = Double.MAX_VALUE;
        String path = "";
        String key = index + " ";
        for(int val : subset){
            key = key + val + " ";
        }
        int minIndex;
        if(d.containsKey(key)){
            return key;
        }

        if(subset.length == 0){
            min = g.get(index, 0);
            path = index + ", " + 0;
        }else{
            for(int j : subset){
                int [] temp = new int[subset.length-1];
                int k = 0;
                for(int i : subset){
                    if(i != j){
                        temp[k] = i;
                        k++;
                    }
                }
                
                String nextPathKey = D(j, temp, g, d, p);
                double val = g.get(index, j) + d.get(nextPathKey);
                
                if(val < min){
                    path = index + ", " + p.get(nextPathKey);
                    min = val;
                    minIndex = j;
                }
            }
        }
        
        d.put(key, min);
        p.put(key, path);
        return key;
    }

    public static void branchTravel(int n, Graph W){
        Node u, v;
        int [] optpath = new int[n];
        PriorityQueue<Node> pq = new PriorityQueue();
        double minlength;
        v = new Node(0);
        v.path = new int[n];
        v.bound = bound(v, W);
        minlength = Integer.MAX_VALUE;
        pq.add(v);
        while(!pq.isEmpty()){
            v = pq.poll();
            if(v.bound < minlength){
                for(int i = 1; i < n; i++){ 
                    if(v.path[i] == 0 && v.last != i){
                        u = new Node(v.level+1);
                        u.path = new int[n];
                        for(int index = 0; index < n; index++){
                            u.path[index] = v.path[index];
                        }
                        u.path[v.last] = i;
                        u.last = i;
                        if(u.level == n - 2){
                            boolean flag = false;
                            u.path[i] = -1;
                            for(int j = 1; j < n && !flag; j++){
                                if(u.path[j] == 0){
                                    u.path[i] = j;
                                    flag = true;
                                }
                            }
                            if(length(u, W) < minlength){
                                minlength = length(u, W);
                                optpath = u.path;
                            }
                        }else{
                            u.bound = bound(u, W);
                            if(u.bound < minlength){
                                pq.add(u);
                            }
                        }
                    }
                }
            }
        }

        System.out.print("Path: " + 0 + ", ");
        int i = optpath[0];
        while(i != 0){
            System.out.print(i + ", ");
            i = optpath[i];
        }
        System.out.println(0 + " Length: " + minlength);
    }

    public static double bound(Node n, Graph g){
        int i, j;
        int [] path = n.path;
        double bound = length(n, g); //bound starts with the length of the node currently
        double min;
        for(i = 0; i < path.length; i++){
            min = Integer.MAX_VALUE;
            if(path[i] == 0){
                for(j = 0; j < g.size(); j++){
                    if(i != j && g.get(i, j) < min){
                        min = g.get(i, j);
                    }
                }
                bound = bound + min;
            }
        }
        return bound;
    }

    // Calculate the length of a path in Node n
    public static double length(Node n, Graph g){
        int [] path = n.path;
        int i, j;

        double length = g.get(0, path[0]);
        i = path[0];
        j = 1;
        while(path[i] != 0){
            //System.out.println("ahhh");
            length = length + g.get(i, path[i]);
            i = path[i];
            j++;
        }
        if(j == path.length-1){
            length = length + g.get(i, 0);
        }
        return length;
    }

    public static class Node implements Comparable<Node>{
        double bound;
        int[] path;
        int last;
        int level;

        public Node(int level){
            bound = -1.0;
            this.level = level;
            //path = new int[level+1];
            last = 0;
        }

        public int compareTo(Node other){
            return (int)(this.bound - other.bound);
        }

        // Prints contents of path[] for debugging purposes
        public void printPath(){
            for(int val : path){
                System.out.print(val + ", ");
            }
            System.out.println();
        }
    }
}

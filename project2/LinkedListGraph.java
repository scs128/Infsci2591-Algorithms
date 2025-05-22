import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class LinkedListGraph {
    LinkedList<Edge> [] vertices;
    int size;
    int [][] P;
    int [][] D;
    int [][] touch;

    @SuppressWarnings("unchecked")
    public LinkedListGraph(String path){
        // Parse to end of file to get final node id to initialize size.
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line = br.readLine();
            String [] data = null;
            while(line != null){
                data = line.split(",");
                line = br.readLine();
            }
            if(data != null){
                size = Integer.parseInt(data[0]) + 1;
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        vertices = new LinkedList[size];

        for(int i = 0; i < size; i++){
            vertices[i] = new LinkedList<Edge>();
        }

        //parse csv edges
        String line;
        String csvSplitBy = ",";

        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            line = br.readLine();
            while((line = br.readLine()) != null){
                String[] data = line.split(csvSplitBy);
                vertices[Integer.parseInt(data[0])].add(new Edge(Integer.parseInt(data[1]), Integer.parseInt(data[2])));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void Dijkstras(){
        //System.out.println("Running Linked List Based Dijkstras Algorithm");
        
        touch = new int[size][size];
        int [] length = new int[size];
        int vnear = -1;



        for(int k = 0; k < size; k++){
            // initialize arrays
            for(int j = 0; j < size; j++){
                touch[k][j] = k;
                length[j] = findWeight(k, j);
            }

            for(int i = 0; i < size; i++){ 
                int min = Integer.MAX_VALUE;

                for(int j = 0; j < size; j++){
                    if(length[j] >= 0 && length[j] < min){
                        min = length[j];
                        vnear = j;
                    }
                }

                //considered[vnear] = true;
                for(int j = 0; j < size; j++){
                    if(findWeight(vnear, j) > 0 && (length[vnear] + findWeight(vnear, j) < length[j] || length[j] == -1)){ //ISSUE: was using -1 to represent both no connection in adjacency and visited in length
                        length[j] = length[vnear] + findWeight(vnear, j);
                        touch[k][j] = vnear;
                    }
                }
                length[vnear] = -2;
            }
        }
    }

    public void printDijkstrasPath(int start, int end){
        // Print path from start to end
        System.out.println("Printing Linked List Dijkstras Path Result");
        int i = end;
        String result = "V" + end;
        int distance = 0;
        while(i != start){
            result = "V" + touch[start][i] + ", " + result; 
            distance += findWeight(touch[start][i], i);
            i = touch[start][i];
        }
        System.out.println("Path: " + result);
        System.out.println("Distance: " + distance + " feet\n");
    }

    // FLOYD'S ALGORITHM
    @SuppressWarnings("unchecked")
    public void Floyds(){
        //System.out.println("Running Linked List Based Floyds Algorithm");
        D = new int[size][size];
        P = new int[size][size];

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                P[i][j] = 0;
                D[i][j] = findWeight(i, j);
            }
        }

        for(int k = 0; k < size; k++){
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    if(D[i][k] != -1 && D[k][j] != -1 && (D[i][j] == -1 || D[i][k] + D[k][j] < D[i][j])){ //account for no connection being represented by -1 and not infinity
                        P[i][j] = k;
                        D[i][j] = D[i][k] + D[k][j];
                    }
                }
            }
        }
    }

    /*--------- Supporting methods to Floyd's algorithm -----------*/
    public void printFloydsPath(int start, int end){
        System.out.println("Printing Linked List Floyds Path Result");
        System.out.print("Path: V" + start + ", ");
        path(start, end);
        System.out.println("V" + end);
        System.out.println("Distance: " + D[start][end] + " feet\n");
    }

    // Get weight of edge from start vertex to end vertex
    public int findWeight(int start, int end){
        if(start == end){
            return 0;
        }
        for(int i = 0; i < vertices[start].size(); i++){
            if(vertices[start].get(i).getVertex() == end){
                return vertices[start].get(i).getWeight();
            }
        }
        return -1;
    }

    // Floyds Algorithm Path Printing
    public void path(int start, int end){
        try{
            //System.out.println(P[start][end]);
            if(P[start][end] != 0){
                path(start, P[start][end]);
                System.out.print("V" + P[start][end] + ", ");
                path(P[start][end], end);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private class Edge{
        int targetVertex;
        int weight;

        private Edge(int vertex, int weight){
            targetVertex = vertex;
            this.weight = weight;
        }

        public int getVertex(){
            return targetVertex;
        }

        public int getWeight(){
            return weight;
        }
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArrayGraph {
    int [][] adjacency;
    int [][] P;
    int [][] D;
    int size; // # of nodes
    int [][] touch;

    public ArrayGraph(String path){
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

        adjacency = new int [size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(i == j){
                    adjacency[i][j] = 0;
                }else{
                    adjacency[i][j] = -1;
                }
            }
        }

        //parse csv edges
        String line;
        String csvSplitBy = ",";

        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            line = br.readLine();
            while((line = br.readLine()) != null){
                String[] data = line.split(csvSplitBy);
                adjacency[Integer.parseInt(data[0])][Integer.parseInt(data[1])] = Integer.parseInt(data[2]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String toString(){
        String result = "";
        for(int i = 0; i < size; i++){
            result = result + " " + (i+1);
        }

        for(int i = 0; i < size; i++){
            result = result + "\n" + (i+1);
            for(int val : adjacency[i]){
                result = result + " " + val;
            }
        }

        return result;
    }


    // Computes all shortest paths from one node to another
    public void Dijkstras(){
        //System.out.println("Running 2D Array Based Dijkstras Algorithm");
        touch = new int[size][size];
        int [] length = new int[size];
        int vnear = -1;


        for(int k = 0; k < size; k++){
            // initialize arrays
            for(int i = 0; i < size; i++){
                touch[k][i] = k;
                length[i] = adjacency[k][i];
            }

            for(int i = 0; i < size; i++){ 
                int min = Integer.MAX_VALUE;

                for(int j = 0; j < size; j++){
                    if(length[j] >= 0 && length[j] < min){
                        min = length[j];
                        vnear = j;
                    }
                }

                for(int j = 0; j < size; j++){
                    if(adjacency[vnear][j] > 0 && (length[vnear] + adjacency[vnear][j] < length[j] || length[j] == -1)){ //ISSUE: was using -1 to represent both no connection in adjacency and visited in length
                        length[j] = length[vnear] + adjacency[vnear][j];
                        touch[k][j] = vnear;
                    }
                }
                length[vnear] = -2;
            }
        }
    }

    public void printDijkstrasPath(int start, int end){
        // Print path from start to end
        System.out.println("Printing 2D Array Dijkstras Path Result");
        int i = end;
        String result = "V" + end;
        int distance = 0;
        while(i != start){
            result = "V" + touch[start][i] + ", " + result; 
            distance += adjacency[touch[start][i]][i];
            i = touch[start][i];
            //System.out.println("AHHHHHHH");
        }
        System.out.println("Path: " + result);
        System.out.println("Distance: " + distance + " feet\n");
    }

    public void Floyds(){
        //System.out.println("Running 2D Array Based Floyds Algorithm");
        D = new int[size][size];
        P = new int[size][size];

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                P[i][j] = 0;
                D[i][j] = adjacency[i][j];
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

    public void printFloydsPath(int start, int end){
        // print path
        System.out.println("Printing 2D Array Floyds Path Result");
        System.out.print("Path: V" + start + ", ");
        path(start, end);
        System.out.println("V" + end);
        System.out.println("Distance: " + D[start][end] + " feet\n");
    }

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
}   
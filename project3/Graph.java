import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph {
    double [][] adjacency;
    int size;

    public Graph(String path){
        // Parse to end of file to get final node id to initialize size.
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line = br.readLine();
            String [] data = null;
            int i = 0;
            while(line != null){
                i++;
                data = line.split(",");
                line = br.readLine();
            }
            size = i;
        }catch(IOException e){
            e.printStackTrace();
        }

        adjacency = new double [size][size];
        
        String line;
        String csvSplitBy = ",";

        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            int i = 0;
            while((line = br.readLine()) != null){
                String[] data = line.split(csvSplitBy);
                for(int j = 0; j < size; j++){
                    adjacency[i][j] = Double.parseDouble(data[j]);
                }
                i++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public double get(int from, int to){
        return adjacency[from][to];
    }

    public int size(){
        return size;
    }

    public String toString(){
        String result = "";
        for(double [] row : adjacency){
            for(double val : row){
                result = result + val + ", ";
            }
            result = result + "\n";
        }
        return result;
    }
}

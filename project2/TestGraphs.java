import java.io.File;

public class TestGraphs {
    public static void main(String[] args) {

        ArrayGraph graph1 = new ArrayGraph("./Project2_Input_File/Project2_Input_File4.csv");
        LinkedListGraph graph2 = new LinkedListGraph("./Project2_Input_File/Project2_Input_File4.csv");

        graph1.Dijkstras();
        graph1.Floyds();
        graph2.Dijkstras();
        graph2.Floyds();

        System.out.println("Test Case 1 Outputs: ");
        graph1.printDijkstrasPath(192, 163);
        graph2.printDijkstrasPath(192, 163);
        graph1.printFloydsPath(192, 163);
        graph2.printFloydsPath(192, 163);

        System.out.println("Test Case 2 Outputs: ");
        graph1.printDijkstrasPath(138, 66);
        graph2.printDijkstrasPath(138, 66);
        graph1.printFloydsPath(138, 66);
        graph2.printFloydsPath(138, 66);

        System.out.println("Test Case 3 Outputs: ");
        graph1.printDijkstrasPath(465, 22);
        graph2.printDijkstrasPath(465, 22);
        graph1.printFloydsPath(465, 22);
        graph2.printFloydsPath(465, 22);


        // Input Runtimes
        File folder = new File("./Project2_Input_File");
        File [] listFiles = folder.listFiles();

        ArrayGraph [] arrayGraphs = new ArrayGraph[listFiles.length];
        long [] arrayTime = new long[listFiles.length];
        long start = 0;
        long end = 0;

        System.out.println("Running 2D Array Graph Dijkstras Algorithm");
        for(int i = 0; i < listFiles.length; i++){
            
            if(listFiles[i].getName().contains("Project2")){
                String name = listFiles[i].getName();
                System.out.println("Handling " + name);
                start = System.nanoTime();
                arrayGraphs[i] = new ArrayGraph("./Project2_Input_File/" + name);
                arrayGraphs[i].Dijkstras();
                end = System.nanoTime();
                arrayTime[i] = end - start;
                System.out.println("Runtime " + arrayTime[i] + "\n");
            }
            
        }
        
        System.out.println("Running 2D Array Graph Floyds Algorithm");
        for(int i = 0; i < listFiles.length; i++){
            if(listFiles[i].getName().contains("Project2")){
                String name = listFiles[i].getName();
                System.out.println("Handling " + name);
                start = System.nanoTime();
                arrayGraphs[i] = new ArrayGraph("./Project2_Input_File/" + name);
                arrayGraphs[i].Floyds();
                end = System.nanoTime();
                arrayTime[i] = end - start;
                System.out.println("Runtime " + arrayTime[i] + "\n");
            }
        }
                
        LinkedListGraph [] linkedListGraphs = new LinkedListGraph[listFiles.length];
        long [] linkedTime = new long[listFiles.length];

        System.out.println("Running Linked List Graph Dijkstras Algorithm");
        for(int i = 0; i < listFiles.length; i++){
            if(listFiles[i].getName().contains("Project2")){
                String name = listFiles[i].getName();
                System.out.println("Handling " + name);
                start = System.nanoTime();
                linkedListGraphs[i] = new LinkedListGraph("./Project2_Input_File/" + name);
                linkedListGraphs[i].Dijkstras();
                end = System.nanoTime();
                linkedTime[i] = end - start;
                System.out.println("Runtime: " + linkedTime[i] + "\n");
            }
        }
        
        System.out.println("Running Linked List Graph Floyds Algorithm");
        for(int i = 0; i < listFiles.length; i++){
            if(listFiles[i].getName().contains("Project2")){
                String name = listFiles[i].getName();
                System.out.println("Handling " + name);
                start = System.nanoTime();
                linkedListGraphs[i] = new LinkedListGraph("./Project2_Input_File/" + name);
                linkedListGraphs[i].Floyds();
                end = System.nanoTime();
                linkedTime[i] = end - start;
                System.out.println("Runtime: " + linkedTime[i] + "\n");
            }
        }
    }
}

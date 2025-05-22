import java.util.PriorityQueue;

public class Knapsack {
    static int [] value = {20, 30, 35, 12, 3};
    static int [] weight = {2, 5, 7, 3, 1};

    public static void main(String [] args){
        bestFirst(13);
    }

    public static void bestFirst(int maxWeight){
        PriorityQueue<Node> pq = new PriorityQueue();
        Node u, v;
        int maxProfit;
        v = new Node();
        u = new Node();

        maxProfit = 0;
        v.bound = bound(v, maxWeight);
        pq.add(v);
        while(pq.size() > 0){
            v = pq.poll();
            System.out.println("Look at next node in queue:");
            System.out.println("Bound: " + v.bound + ", Weight: " + v.weight + ", Max Profit: " + maxProfit);
            if(v.bound > maxProfit){
                u.level = v.level + 1;
                u.weight = v.weight + weight[u.level];
                u.profit = v.profit + value[u.level];

                if(u.weight <= maxWeight && u.profit > maxProfit){
                    maxProfit = u.profit;
                }
                u.bound = bound(u, maxWeight);
                if(u.bound > maxProfit){
                    System.out.println("Add Node including item " + (u.level+1) + " to the queue, bound = " + u.bound);
                    pq.add(u);
                } else {
                    System.out.println("Bound is less than max profit, do not add node including item " + (u.level+1));
                }
                u.weight = v.weight;
                u.profit = v.profit;
                u.bound = bound(u, maxWeight);
                if(u.bound > maxProfit){
                    System.out.println("Add Node excluding item " + (u.level+1) + " to the queue, bound = " + u.bound);
                    pq.add(u); 
                } else {
                    System.out.println("Bound is less than max profit, do not add node including item " + (u.level+1));
                }
                System.out.println();
            }
        }
    }

    public static float bound(Node u, int maxWeight){
        int j, k;
        int totweight;
        float result;
        if(u.weight >= maxWeight){
            return 0;
        }else{
            result = u.profit;
            j = u.level + 1;
            totweight = u.weight;
            while(j < value.length && totweight + weight[j] <= maxWeight){
                totweight = totweight + weight[j];
                result = result + value[j];
                j++;
            }
            k = j;
            if(k < value.length){
                result = result + (maxWeight - totweight) * value[k]/weight[k];
            }

            return result;
        }
    }

    public static class Node implements Comparable<Node>{
        int level;
        int profit;
        int weight;
        float bound;

        public Node(){
            level = -1;
            profit = 0;
            weight = 0;
            bound = 0;
        }

        @Override
        public int compareTo(Node other){
            return (int)(this.bound - other.bound);
        }
    }
}

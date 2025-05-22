public class CommonElements {
    public static void main (String [] args){
        int [] S = {2, 5, 7, 14, 19, 20};
        int [] T = {4, 5, 6, 7, 13, 20};
        //int [] T = {3, 6, 8, 15, 18, 21};
        int [] U = new int[S.length + T.length];

        int i, j, k;

        i = 0;
        j = 0;
        k = 0;
        int comparisons = 0;
        while(i < S.length && j < T.length){
            comparisons++;
            if(S[i] == T[j]){
                U[k] = S[i];
                i++;
                j++;
                k++;
            }else if(S[i] < T[j]){
                i++;
            }else{
                j++;
            }
        }

        for(i = 0; i < k; i++){
            System.out.print(U[i] + ", ");
        }
        System.out.println("\n" + comparisons);
    }
}

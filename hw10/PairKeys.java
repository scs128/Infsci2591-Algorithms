public class PairKeys {
    public static void main(String[]args){
        int [] s = {5, 3, 7, 12, 2, 24, 9, 13, 31, 1};
        find_both2(s.length, s);
    }

    public static void find_both2(int n, int [] S){
        int small;
        int large;
        int i;
        if(S[0] < S[1]){ // one comparison
            small = S[0];
            large = S[1];
        }else{
            small = S[1];
            large = S[0];
        }
        for(i = 2; i < n-1; i = i +2){ //when even this runs n-2/2 times. when odd its runs n-3/2 times. multiply by 3
            if(S[i] < S[i+1]){ //one comparison
                if(S[i] < small){ //two comparison
                    small = S[i];
                }
                if(S[i+1] > large){ //three comparison
                    large = S[i+1];
                }
            }else{
                if(S[i+1] < small){
                    small = S[i+1]; 
                }                   
                if(S[i] > large){
                    large = S[i];
                }
            }
        }
        if(n%2 == 1){
            if(S[n-1] < small){ //one comparison
                small = S[n-1]; 
            }else if(S[n-1] > large){ //two comparison
                large = S[n-1];
            }
        }
        

        System.out.println("Small: " + small + "\nLarge: " + large);
    }
}

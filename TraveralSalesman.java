package Google2015;

import java.util.Arrays;

/**
 * Created by Trace_route on 2/24/15.
 */
public class TraveralSalesMan {
    public static int getShortestHamiltonianCycle(int[][] matrix){
        int n = matrix.length;
        int[][] dp = new int[1<<n][n];
        for(int[] d:dp)
            Arrays.fill(d,Integer.MAX_VALUE/2);
        dp[1][0] = 0; //
        for(int mask = 1;mask<1<<n;mask++){
            for(int i =0;i<n;i++){
                if((mask&(1<<i)) !=0 )
                for(int j =0;j<n;j++){
                    if((mask&(1<<j))!=0)
                        dp[mask][i] = Math.min(matrix[i][j]+dp[mask^(1<<i)][j],dp[mask][i]);
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for(int i =1;i<n;i++){
            min= Math.min(dp[(1<<n) -1][i] + matrix[i][0], min);
        }
        int[] seq = new int[n];
        int last = 0;
        int set = (1<<n)-1;
        for(int i = n-1;i>0;i--){//n-1 nodes
            int record = -1;
            for(int j =1;j<n;j++){ // first node can be ignored
                if((set&(1<<j))!=0 && (record==-1 || dp[set][record]+matrix[record][last]>dp[set][j]+matrix[j][last]))
                    record = j;
            }
            seq[i] = record;
            set^=(1<<record);
            last = record;
        }
        System.out.println(Arrays.toString(seq));
        return min;

    }
    public static void main(String[] args){
        int[][] dist = { { 0, 1, 10, 1, 10 }, { 1, 0, 10, 10, 1 }, { 10, 10, 0, 1, 1 }, { 1, 10, 1, 0, 10 },
                { 10, 1, 1, 10, 0 } };
        System.out.println( getShortestHamiltonianCycle(dist));
//        System.out.println(tsp(dist));
    }
}

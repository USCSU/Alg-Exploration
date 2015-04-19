package AfterEpicAndGoogle;

import sun.plugin.javascript.navig.Array;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Trace_route on 4/18/15.
 */
public class SnakeSeq {
    public static void main(String[] args){
        int[][] grid =
//                {{1, 3, 2, 0, 8},
//                {-9, 7, 1,-1, 2},
//                { 1, 5, 0, 1, 9}};
                       {{2, 3, 2, 0, 8},
                        {1, 2, 1,-1, 2},
                        {2, 4, 0, 1, 9}};

//        int[][] grid =
//                {{3,4,5,6,7},
//                 {2,3,4,5,6},
//                 {1,2,3,4,5},
//
//                 };
//        snakeSequence(grid);
        for(int[] d:grid)
        System.out.println(Arrays.toString(d));
        System.out.println("Two Way path : " +getLongestSnake(grid));//(O(m^2n^2))
        System.out.println("Four Way Path : " +getLongestSnakeFourWay(grid)); //O(m^2n^2)
        System.out.println("Two Way Dp:  "+ LongestSnakeTwoWayDp(grid)); //O(mn)
        System.out.println("Four Way Dp:  "+ LongestSnakeFourWayDp(grid));//O(mn)
    }
    //two ways, right and down; not cycle
    public static ArrayList<Integer> getLongestSnake(int[][] grid){
        if(grid == null || grid.length == 0) return null;//error code
        ArrayList<Integer> lists = new ArrayList<Integer>();

        for(int i =0;i<grid.length;i++){
            for(int j =0;j<grid[0].length;j++){
                  getLongestSnakeHelper(lists,new ArrayList<Integer>(),grid,i,j,grid[i][j]-1);
            }
        }
        return lists;
    }
    public static void getLongestSnakeHelper(ArrayList<Integer> max, ArrayList<Integer> cur, int[][] grid, int x, int y, int val){
        //base
        if(x < 0||x==grid.length||y<0 ||y ==grid[0].length|| Math.abs(val - grid[x][y])!=1){
            if(cur.size() > max.size()){
                max.clear();;
                max.addAll(cur);
            }
            return ;
        }
        //recurisve
        cur.add(grid[x][y]);
        getLongestSnakeHelper(max, cur, grid, x + 1, y, grid[x][y]);
        getLongestSnakeHelper(max, cur, grid, x, y + 1, grid[x][y]);

        cur.remove(cur.size()-1);
    }
    //four ways direction, cycle may existed
    public static ArrayList<Integer> getLongestSnakeFourWay(int[][] grid){
        if(grid == null || grid.length ==0) return null;
        int row = grid.length, col = grid[0].length;
        boolean[][] checker = new boolean[row][col];
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for(int i =0;i<grid.length;i++)
        {
            for(int j =0;j<grid[0].length;j++){
                getLongestSnakeFourWayHelper(ret, new ArrayList<Integer>(),checker,grid,i,j,grid[i][j] - 1);
            }
        }
        return ret;
    }
    public static void getLongestSnakeFourWayHelper(ArrayList<Integer> max, ArrayList<Integer> cur,boolean[][] checker, int[][] grid, int x, int y, int val){
        if(x < 0 || x == grid.length || y <0 || y == grid[0].length || checker[x][y] || Math.abs(val - grid[x][y])!=1){
            if(max.size() < cur.size()){
                max.clear();
                max.addAll(cur);
            }
            return ;
        }
        checker[x][y] = true;
        cur.add(grid[x][y]);
        getLongestSnakeFourWayHelper(max, cur, checker, grid, x - 1, y, grid[x][y]);
        getLongestSnakeFourWayHelper(max, cur, checker, grid, x + 1, y, grid[x][y]);
        getLongestSnakeFourWayHelper(max, cur, checker, grid, x, y + 1, grid[x][y]);
        getLongestSnakeFourWayHelper(max,cur,checker,grid,x,y-1,grid[x][y]);
        cur.remove(cur.size() - 1);
        checker[x][y] = false;
    }
    //two way search Dp
    public static int LongestSnakeTwoWayDp(int[][] grid){
        if(grid == null || grid.length ==0 ) return 0;
        int max = 0;
        int row = grid.length, col = grid[0].length;
        int[][] dp = new int[row][col];
        for(int i = row-1;i>=0;i--){
            for(int j = col-1;j>=0;j--){
                if(i == row-1&&j==col-1){
                    dp[i][j] = 1; continue;
                }
                else if(i == row-1)
                    dp[i][j] = Math.abs(grid[i][j] - grid[i][j+1])==1?dp[i][j+1]+1:1;
                else if( j == col -1)
                    dp[i][j] = Math.abs(grid[i][j] - grid[i+1][j]) == 1? dp[i+1][j] + 1: 1;
                else{
                    int right = Math.abs(grid[i][j] - grid[i][j+1]) == 1? dp[i][j+1] + 1: 1;
                    int down =  Math.abs(grid[i][j] - grid[i+1][j]) == 1? dp[i+1][j] + 1: 1;
                    dp[i][j] = Math.max(right,down);
                }
                max = Math.max(dp[i][j],max);
            }
        }
        return max;
    }
    public static int LongestSnakeFourWayDp(int[][] grid){
        if(grid == null || grid.length == 0) return 0;
        int m = grid.length, n = grid[0].length;
        int[][] dp1 = new int[m][n];
        int[][] dp2 = new int[m][n];
        int max =0;
        //bottom right --> top left
        for(int i =m -1; i>=0;i--){
            for(int j = n-1; j>=0;j--){
                if(i == m-1 && j == n-1){
                    dp1[i][j] = 1; continue;
                }
                else if( i== m-1){
                    dp1[i][j] = Math.abs(grid[i][j] - grid[i][j+1]) == 1?dp1[i][j+1]+1:1;
                }else if(j == n-1){
                    dp1[i][j] = Math.abs(grid[i][j] - grid[i+1][j]) == 1? dp1[i+1][j] + 1: 1;
                }else{
                    int right  = Math.abs(grid[i][j] - grid[i+1][j]) == 1? dp1[i+1][j] + 1: 1;
                    int down  = Math.abs(grid[i][j] - grid[i][j+1]) == 1?dp1[i][j+1]+1:1;
                    dp1[i][j] = Math.max(right,down);
                }
            }
        }
        for(int[] d:dp1)
            System.out.println(Arrays.toString(d));
        //bottom right -- > top left
        for(int i = 0;i<m;i++){
            for(int j =0;j<n;j++){
                if(i ==0&&j==0){
                    dp2[i][j] = 1; continue;
                }
                else if(i == 0){
                    dp2[i][j] = Math.abs(grid[i][j] - grid[i][j-1]) == 1?dp2[i][j-1] + 1:1;
                }else if(j ==0){
                    dp2[i][j] = Math.abs(grid[i][j] - grid[i-1][j]) == 1?dp2[i-1][j] + 1:1;
                }else {
                    int left = Math.abs(grid[i][j] - grid[i][j-1]) == 1?dp2[i][j-1] + 1:1;
                    int top =  Math.abs(grid[i][j] - grid[i-1][j]) == 1?dp2[i-1][j] + 1:1;
                    dp2[i][j] = Math.max(left,top);
                }
            }
        }
        for(int[] d:dp2)
                System.out.println(Arrays.toString(d));
        //combine
        for(int i = 0; i<m;i++){
            for(int j =0;j<n ;j++){
                max = Math.max(max,dp1[i][j]+dp2[i][j]);
            }
        }
        return max;
    }
}

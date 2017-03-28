package edu.princeton.cs.algs4;

/**
 * Created by yixiangwu on 3/27/17.
 */
public class PercolationStats {
    // perform trials independent experiments on an n-by-n grid
    private double [] ans;
    private Percolation pr;
    private int t;
    public PercolationStats(int n, int trials){
        if(n <=0 || trials <= 0) throw new IllegalArgumentException();
        ans = new double[trials];
        t   = trials;
        for (int i = 0; i < trials; i++){
            pr  = new Percolation(n);
            int openedSites = 0;
            while(!pr.percolates()){
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);
                pr.open(x,y);
            }
            ans[i] = ( (double) pr.numberOfOpenSites() / (double) (n*n));
        }
    }
    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(ans);

    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(ans);
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(t));
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(t));
    }

    // test client (described below)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}

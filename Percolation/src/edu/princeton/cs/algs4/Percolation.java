package edu.princeton.cs.algs4;

/**
 * Created by yixiangwu on 3/20/17.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // create n-by-n grid, with all sites blocked
    private boolean [][] grid;
    private WeightedQuickUnionUF uf;
    private int top, bottom;
    private final int N;
    private int numberOfOpenedSites;

    public Percolation(int n){
        grid   = new boolean[n][n];
        uf     = new WeightedQuickUnionUF(n*n + 2);
        top    = 0;
        bottom = n*n +1;
        N      = n;
        numberOfOpenedSites =0;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col){
        check(row,col);

        if (!grid[row-1][col-1]){
            grid[row-1][col-1] = true;
            numberOfOpenedSites++;
        }

        int c = convertXY(row,col);
        if(row == 1){
            uf.union(top,c);
        }
        if (row == N){
            uf.union(bottom,c);
        }
        if (row > 1 && isOpen(row-1,col)){
            uf.union(c,convertXY(row-1,col));
        }
        if (row < N && isOpen(row+1,col)){
            uf.union(c,convertXY(row+1,col));
        }
        if (col > 1 && isOpen(row,col-1)){
            uf.union(c,convertXY(row,col-1));
        }
        if (col < N && isOpen(row,col+1)){
            uf.connected(c,convertXY(row,col+1));
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        check(row,col);
        return grid[row-1][col-1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        check(row,col);
        return uf.connected(top,convertXY(row-1,col-1));
    }

    // number of open sites
    public int numberOfOpenSites(){
        return numberOfOpenedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(top,bottom);
    }

    public void check(int row, int col){
        if (row <= 0 || row > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (col <= 0 || col > N) throw new IndexOutOfBoundsException("row index i out of bounds");
    }

    public int convertXY (int x, int y){
        check(x,y);
        return N*(x-1) + y;
    }
    // test client (optional)
    public static void main(String[] args){
        Percolation p = new Percolation(100);
        while(!p.percolates()){
            int x = StdRandom.uniform(1, 100 + 1);
            int y = StdRandom.uniform(1, 100 + 1);
            p.open(x,y);
        }
        System.out.println(p.percolates());
        System.out.println(p.numberOfOpenSites());
    }

}

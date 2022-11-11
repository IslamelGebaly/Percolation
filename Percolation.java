/*import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;*/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private int top;
    private int bottom;
    private int n;

    private boolean[][] percolationMatrix;
    private int openSites;

    private int indexOf(int row, int col) {
        return (row - 1) * n + col;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("");
        }

        this.n = n;
        this.openSites = 0;
        this.top = 0;
        this.bottom = (n * n + 1);

        uf = new WeightedQuickUnionUF((n + 1) * (n + 1));

        percolationMatrix = new boolean[n + 1][n + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                percolationMatrix[i][j] = false;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (row >= n + 1 || row < 1 || col >= n + 1 || col < 1) {
            throw new IllegalArgumentException();
        }

        return percolationMatrix[row][col];

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row >= n + 1 || row < 1 || col >= n + 1 || col < 1) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col))
            return;

        percolationMatrix[row][col] = true;
        this.openSites++;

        if (row == 1) {
            uf.union(top, indexOf(row, col));
        }
        if (row == n) {
            uf.union(indexOf(row, col), bottom);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(indexOf(row - 1, col), indexOf(row, col));
        }
        if (row < n && isOpen(row + 1, col)) {
            uf.union(indexOf(row, col), indexOf(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(indexOf(row, col), indexOf(row, col - 1));
        }
        if (col < n && isOpen(row, col + 1)) {
            uf.union(indexOf(row, col), indexOf(row, col + 1));
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row >= n + 1 || row < 1 || col >= n + 1 || col < 1) {
            throw new IllegalArgumentException();
        }

        return uf.find(indexOf(row, col)) == uf.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation pr = new Percolation(5);

        pr.open(3, 4);
        StdOut.println(pr.numberOfOpenSites());
        pr.open(3, 3);
        StdOut.println(pr.numberOfOpenSites());
        pr.open(2, 3);
        StdOut.println(pr.numberOfOpenSites());
        pr.open(1, 3);
        StdOut.println(pr.numberOfOpenSites());
        pr.open(4, 4);
        StdOut.println(pr.numberOfOpenSites());
        StdOut.println(pr.isFull(5, 4) + ", " + pr.numberOfOpenSites());
    }
}

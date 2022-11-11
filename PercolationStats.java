import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid

    private double[] thresholds;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.thresholds = new double[trials];

        Percolation p;
        int x, y;
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            this.thresholds[i] = 0.0;
            while (!p.percolates()) {

                x = StdRandom.uniformInt(1, n + 1);
                y = StdRandom.uniformInt(1, n + 1);

                if (!p.isOpen(x, y))
                    p.open(x, y);


            }
            double fraction = p.numberOfOpenSites() / ((n * n) * 1.0);
            thresholds[i] = fraction;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return StdStats.mean(this.thresholds) - 1.96 * StdStats.stddev(this.thresholds) / Math.sqrt(
                this.thresholds.length);

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.mean(this.thresholds) + 1.96 * StdStats.stddev(this.thresholds) / Math.sqrt(
                this.thresholds.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(size, trials);
        String confidence = "[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]";


        StdOut.printf("mean                    = %f\n", stats.mean());
        StdOut.printf("stddev                  = %f\n", stats.stddev());
        StdOut.println("95% confidence interval = " + confidence);

    }
}

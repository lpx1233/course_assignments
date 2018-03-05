import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean = 0;
    private double stddev = 0;
    private int trials = 0;
    
    public PercolationStats(int n, int trials){    // perform trials independent experiments on an n-by-n 
        if((n <= 0) || (trials <= 0)){
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        double[] x = new double[trials];
        Percolation perc = null;
        int row, col = 0;
        for(int i = 0; i < trials; i++){
            perc = new Percolation(n);
            while(!perc.percolates()){
                row = StdRandom.uniform(n) + 1;
                col = StdRandom.uniform(n) + 1;
                perc.open(row, col);
            }
            x[i] = (double)perc.numberOfOpenSites() / ((double)n*n);
        }
        this.mean = StdStats.mean(x);
        this.stddev = StdStats.stddev(x);
    }
    public double mean(){                          // sample mean of percolation threshold
        return mean;
    }
    public double stddev(){                        // sample standard deviation of percolation threshold
        return stddev;
    }
    public double confidenceLo(){                  // low  endpoint of 95% confidence interval
        return mean - 1.96 * stddev / Math.sqrt(trials);
    }
    public double confidenceHi(){                  // high endpoint of 95% confidence interval
        return mean + 1.96 * stddev / Math.sqrt(trials);
    }

    public static void main(String[] args){        // test client (described below)
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("mean                    = %f\n", ps.mean());
        StdOut.printf("stddev                  = %f\n", ps.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", ps.confidenceLo(), ps.confidenceHi());
    }
}
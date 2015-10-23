import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private double[] results;
    
    public PercolationStats(int N, int T){
        
        if (N <= 0)
        {
            throw new IllegalArgumentException("The array size cannot be less than 1.");
        }
        else if (T <= 0)
        {
            throw new IllegalArgumentException("The number of tests cannot be less than 1.");
        }
        
        results = new double[T];
        
        for (int i = 0; i < T; i++){
            Percolation perc = new Percolation(N);
            int threshold = 0;
            
            while(perc.percolates() == false){
                int y = StdRandom.uniform(1, N + 1);
                int x = StdRandom.uniform(1, N + 1);
                
                if (perc.isOpen(y,x) == false){
                    perc.open(y,x);
                    threshold++;
                }
            }
            
            results[i] = ((double)threshold) / (N * N);
            
        }
        
    }     // perform T independent experiments on an N-by-N grid
    public double mean(){
        return StdStats.mean(results);
    }                      // sample mean of percolation threshold
    public double stddev(){
        return StdStats.stddev(results);
    }                    // sample standard deviation of percolation threshold
    public double confidenceLo(){
        double mean = mean();
        double stddev = stddev();
        
        return mean - 1.96 * stddev / Math.sqrt(results.length);
        
        
    }              // low  endpoint of 95% confidence interval
    public double confidenceHi(){
        double mean = mean();
        double stddev = stddev();
        
        return mean + 1.96 * stddev / Math.sqrt(results.length);
    }              // high endpoint of 95% confidence interval

    public static void main(String[] args){
        PercolationStats ps = new PercolationStats(2, 100000);
        
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
        
    
    }    // test client (described below)
}

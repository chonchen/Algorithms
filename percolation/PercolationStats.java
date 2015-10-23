import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * This class implements a Monte Carlo simulation to find the percolation threshold of an N by N grid. It will run a number of
 * percolation experiments specified by the user. These experiments open nodes randomly until the system percolates from top
 * to bottom. The class will then find the average number of nodes that need to be opened to find the percolation threshold.
**/

public class PercolationStats
{
    
    private double[] results;
    
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {
        
        if (N <= 0)
        {
            throw new IllegalArgumentException("The array size cannot be less than 1.");
        }
        else if (T <= 0)
        {
            throw new IllegalArgumentException("The number of tests cannot be less than 1.");
        }
        
        results = new double[T];
        
        for (int i = 0; i < T; i++)
        {
            Percolation perc = new Percolation(N);
            int threshold = 0;
            
            while(perc.percolates() == false)
            {
                int y = StdRandom.uniform(1, N + 1);
                int x = StdRandom.uniform(1, N + 1);
                
                if (perc.isOpen(y,x) == false){
                    perc.open(y,x);
                    threshold++;
                }
            }
            
            results[i] = ((double)threshold) / (N * N);
            
        }
        
    }
    
    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(results);
    }   
    
    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(results);
    }   
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo()
    {
        double mean = mean();
        double stddev = stddev();
        
        return mean - 1.96 * stddev / Math.sqrt(results.length);   
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        double mean = mean();
        double stddev = stddev();
        
        return mean + 1.96 * stddev / Math.sqrt(results.length);
    }              

    // test client
    public static void main(String[] args)
    {
        PercolationStats ps = new PercolationStats(2, 100000);
        
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}

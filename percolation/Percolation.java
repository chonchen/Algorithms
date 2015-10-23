import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * This class creates an N by N grid where N is given by the user. The user can open nodes in the grid one-by-one and check if the
 * system percolates from the top of the grid to the bottom.
**/

public class Percolation
{
    private boolean[][] grid;
    private int dimension;
    private int startpoint = 0;
    private int endpoint;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        
        if (N <= 0) {
            throw new IllegalArgumentException("The array size cannot be less than 1.");
        }
        
        dimension = N;
        
        endpoint = dimension * dimension + 1;
        
        grid = new boolean[dimension][dimension];
        
        uf = new WeightedQuickUnionUF(dimension * dimension + 2);
        uf2 = new WeightedQuickUnionUF(dimension * dimension + 1);
        
    }              
    
    // open site (row i, column j) if it is not open already
    public void open(int i, int j)
    {
        
        if (i <= 0 || i > dimension || j <= 0 || j > dimension) {
            
            throw new IndexOutOfBoundsException("Must open a block inside the array.");
        }
        
        int y = i - 1;
        int x = j - 1;
        
        if (!isOpen(i, j)) {
            
            grid[y][x] = true;
            
            if (i == 1) {
                uf.union(startpoint, gridvalue(i, j));
                uf2.union(startpoint, gridvalue(i, j));
            }
            if (i == dimension) {
                uf.union(endpoint, gridvalue(i, j));
            }
        
            if (i > 1) {
                if (isOpen(i - 1, j)){
                    uf.union(gridvalue(i, j), gridvalue(i - 1, j));
                    uf2.union(gridvalue(i, j), gridvalue(i - 1, j));
                }
            }
            
            if (i < dimension) {
                if (isOpen(i + 1, j)){
                    uf.union(gridvalue(i, j), gridvalue(i + 1, j));
                    uf2.union(gridvalue(i, j), gridvalue(i + 1, j));
                }
            }
            
            if (j > 1){
                if (isOpen(i, j - 1)) {
                   uf.union(gridvalue(i, j), gridvalue(i, j - 1));
                   uf2.union(gridvalue(i, j), gridvalue(i, j - 1));
                }
            }
        
            if (j < dimension){
                if (isOpen(i, j + 1)) {
                    uf.union(gridvalue(i, j), gridvalue(i, j + 1));
                   uf2.union(gridvalue(i, j), gridvalue(i, j + 1));
                }
            }
        }
        
    }          
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        
        if (i <= 0 || i > dimension || j <= 0 || j > dimension) {
            
            throw new IndexOutOfBoundsException("Must choose a block inside the array.");
        }
        
        int y = i - 1;
        int x = j - 1;
        
        if (grid[y][x] == true) {
            return true;
        }
        else {
            return false;
        }
    }     
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        
        if (i <= 0 || i > dimension || j <= 0 || j > dimension) {
            
            throw new IndexOutOfBoundsException("Must look at a block inside the array.");
        }
        
        
        
        if (isOpen(i, j)) {
            
            return uf2.connected(gridvalue(i, j), startpoint);
        }
        else {
            return false;
        }
        
    }
    
    // does the system percolate?
    public boolean percolates()
    {
        return uf.connected(startpoint, endpoint);
    }
    
    //helper function to check if a node is open
    private int gridvalue(int i, int j)
    {
        return (i - 1) * dimension + j;
    }

    // test client
    public static void main(String[] args)
    {    
        Percolation p = new Percolation(5);
        
        p.open(1,1);
        p.open(2,1);
        p.open(3,1);
        p.open(5,1);
        
        System.out.println(p.isFull(5,1));
        System.out.println(p.percolates());
       
        p.open(3,2);
        p.open(3,3);
        p.open(4,3);
        p.open(5,3);
        System.out.println(p.isFull(3,2));
        System.out.println(p.percolates());   
    }
}
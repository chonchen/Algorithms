import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import java.awt.Color;
import java.util.Stack;

public class SeamCarver
{
    private class DirectedVertex
    {
        private int from;
        private int vertex;
        private double energy;
        private double distTo;
        
        public DirectedVertex(int vertex, double energy)
        {
            from = -1;
            this.vertex = vertex;
            this.energy = energy;
            distTo = Double.POSITIVE_INFINITY;
        }
    }
    
    private Picture picture;
    
    private int width;
    
    private int height;
    
    private DirectedVertex[][] energyArray;
      
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {
        this.picture = new Picture(picture);
        
        height = picture.height();
        width = picture.width();
    }
    
    // current picture
    public Picture picture()
    {
        return picture;
    }
    
    // width of current picture 
    public int width()
    {
        return width;
    }
    
    // height of current picture
    public int height()
    {
        return height;
    }
    
    // energy of pixel at column x and row y 
    public double energy(int x, int y)
    {
        if (x == 0 || x == picture.width() - 1
                || y == 0 || y == picture.height() - 1)
        {
            return 1000.00;
        }
        
        double squareRGBX = squareRGB(x - 1, y, x + 1, y);
        double squareRGBY = squareRGB(x, y - 1, x, y + 1);
        
        return Math.sqrt(squareRGBX + squareRGBY);
    }
    
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam()
    {   
        return findSeam(width, height, false);
    }
    
    // sequence of indices for vertical seam
    public int[] findVerticalSeam()
    {   
        DirectedVertex end = new DirectedVertex(-1, 1000);
        
        energyArray = new DirectedVertex[height][width];
        
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                energyArray[y][x] = new DirectedVertex(x, energy(x, y));
            }
        }
        
        for (int x = 0; x < width; x++)
        {
            energyArray[0][x].distTo = 1000;
        }
             
        for (int y = 1; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                DirectedVertex shortest = energyArray[y - 1][x];
                
                if (x - 1 >= 0)
                {
                    if (energyArray[y - 1][x - 1].distTo <= shortest.distTo)
                    {
                        shortest = energyArray[y - 1][x - 1];
                    }
                }
                
                if (x + 1 < width)
                {
                    if (energyArray[y - 1][x + 1].distTo < shortest.distTo)
                    {
                        shortest = energyArray[y - 1][x + 1];
                    }   
                }
                
                energyArray[y][x].from = shortest.vertex;
                energyArray[y][x].distTo = energyArray[y][x].energy + shortest.distTo;
                
                if (y == height - 1)
                {
                    if (energyArray[y][x].distTo < end.distTo)
                    {
                        end = energyArray[y][x];
                    }
                }
            }
            
        }
        
        int[] seam = new int[height];
        
        DirectedVertex traverse = end;
        seam[height - 1] = traverse.vertex;
        
        for (int i = height - 2; i >= 0; i--)
        {
            traverse = energyArray[i][traverse.from];
            seam[i] = traverse.vertex;
        }
        
        return seam;
    }
    
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)
    {}
    
    // remove vertical seam from current picture    
    public void removeVerticalSeam(int[] seam)
    {}
    
    private double squareRGB(int x1, int y1, int x2, int y2)
    {
        Color color1 = picture.get(x1, y1);
        Color color2 = picture.get(x2, y2);
        
        double deltaRed = color1.getRed() - color2.getRed();
        double deltaGreen = color1.getGreen() - color2.getGreen();
        double deltaBlue = color1.getBlue() - color2.getBlue();
        
        return Math.pow(deltaRed, 2) +  Math.pow(deltaGreen, 2) +  Math.pow(deltaBlue, 2);
    }
    
    
    private int[] findSeam(int parentArrayDimension, int childArrayDimension, boolean isVertical)
    {   
        DirectedVertex end = new DirectedVertex(-1, 1000);
        
        energyArray = new DirectedVertex[parentArrayDimension][childArrayDimension];
        
        for (int i = 0; i < parentArrayDimension; i++)
        {
            for (int j = 0; j < childArrayDimension; j++)
            {
                energyArray[i][j] = new DirectedVertex(j, orientEnergy(i, j, isVertical));
            }
        }
        
        for (int j = 0; j < childArrayDimension; j++)
        {
            energyArray[0][j].distTo = 1000;
        }
             
        for (int i = 1; i < parentArrayDimension; i++)
        {
            for (int j = 0; j < childArrayDimension; j++)
            {
                DirectedVertex shortest = energyArray[i - 1][j];
                
                if (j - 1 >= 0)
                {
                    if (energyArray[i - 1][j - 1].distTo <= shortest.distTo)
                    {
                        shortest = energyArray[i - 1][j - 1];
                    }
                }
                
                if (j + 1 < childArrayDimension)
                {
                    if (energyArray[i - 1][j + 1].distTo < shortest.distTo)
                    {
                        shortest = energyArray[i - 1][j + 1];
                    }   
                }
                
                energyArray[i][j].from = shortest.vertex;
                energyArray[i][j].distTo = energyArray[i][j].energy + shortest.distTo;
                
                if (i == parentArrayDimension - 1)
                {
                    if (energyArray[i][j].distTo < end.distTo)
                    {
                        end = energyArray[i][j];
                    }
                }
            }
            
        }
        
        int[] seam = new int[parentArrayDimension];
        
        DirectedVertex traverse = end;
        seam[parentArrayDimension - 1] = traverse.vertex;
        
        for (int i = parentArrayDimension - 2; i >= 0; i--)
        {
            traverse = energyArray[i][traverse.from];
            seam[i] = traverse.vertex;
        }
        
        return seam;
    }
    
    private double orientEnergy(int i, int j, boolean isVertical)
    {
        if (isVertical)
        {
            return energy(j, i);
        }
        else
        {
            return energy(i, j);
        }
    
    }
    
}

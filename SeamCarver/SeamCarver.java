import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver
{   
    private int width;
    
    private int height;
    
    private Color[][] picColorValues;
      
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {   
        if (picture == null)
        {
            throw new NullPointerException("Please construct with a picture");
        }
        
        width = picture.width();
        height = picture.height();
        picColorValues = new Color[width][height];
        
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                picColorValues[x][y] = picture.get(x, y);
            }
        }
    }
    
    // current picture
    public Picture picture()
    {
        Picture picture = new Picture(width, height);
        
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                picture.set(x, y, picColorValues[x][y]);
            }
        }
        
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
        if (x < 0 || x >= width || y < 0 || y >= height)
        {
            throw new IndexOutOfBoundsException("x and y must be within the picture");
        }
        
        if (x == 0 || x == width - 1
                || y == 0 || y == height - 1)
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
        return findSeam(height, width, true);
    }
    
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)
    {
        seamValidation(seam, false);
        
        Color[][] temp = new Color[width][height - 1];
        
        for (int x = 0; x < width; x++)
        {
            int tempY = 0;
            
            for (int y = 0; y < height; y++)
            {
                if (y != seam[x])
                {
                    temp[x][tempY] = picColorValues[x][y];
                    tempY++;
                }
            }
        }
        
        picColorValues = temp;
        height--;
    }
    
    // remove vertical seam from current picture    
    public void removeVerticalSeam(int[] seam)
    {
        seamValidation(seam, true);
        
        Color[][] temp = new Color[width - 1][height];
        
        for (int y = 0; y < height; y++)
        {
            int tempX = 0;
            
            for (int x = 0; x < width; x++)
            {
                if (x != seam[y])
                {
                    temp[tempX][y] = picColorValues[x][y];
                    tempX++;
                } 
            }
        }
        
        picColorValues = temp;
        width--;
    
    }
    
    private double squareRGB(int x1, int y1, int x2, int y2)
    {
        Color color1 = picColorValues[x1][y1];
        Color color2 = picColorValues[x2][y2];
        
        double deltaRed = color1.getRed() - color2.getRed();
        double deltaGreen = color1.getGreen() - color2.getGreen();
        double deltaBlue = color1.getBlue() - color2.getBlue();
        
        return deltaRed * deltaRed + deltaGreen * deltaGreen + deltaBlue * deltaBlue;
    } 
    
    private int[] findSeam(int parentArrayDimension, int childArrayDimension, boolean isVertical)
    {   
        double[][] energy = new double[parentArrayDimension][childArrayDimension];
        double[][] sumEnergy = new double[parentArrayDimension][childArrayDimension];
        int[][] fromPaths = new int [parentArrayDimension][childArrayDimension];
        int shortestEndVertex = 0;
        
        for (int i = 0; i < parentArrayDimension; i++)
        {
            for (int j = 0; j < childArrayDimension; j++)
            {
                energy[i][j] = orientEnergy(i, j, isVertical);
            }
        }
        
        for (int j = 0; j < childArrayDimension; j++)
        {
            sumEnergy[0][j] = 1000;
        }
             
        for (int i = 1; i < parentArrayDimension; i++)
        {
            for (int j = 0; j < childArrayDimension; j++)
            {
                int shortestPathUp = j;
                
                if (j - 1 >= 0)
                {
                    if (sumEnergy[i - 1][j - 1] <= sumEnergy[i - 1][shortestPathUp])
                    {
                        shortestPathUp = j - 1;
                    }
                }
                
                if (j + 1 < childArrayDimension)
                {
                    if (sumEnergy[i - 1][j + 1] < sumEnergy[i - 1][shortestPathUp])
                    {
                        shortestPathUp = j + 1;
                    }   
                }
                
                fromPaths[i][j] = shortestPathUp;
                sumEnergy[i][j] = energy[i][j] + sumEnergy[i - 1][shortestPathUp];
                
                if (i == parentArrayDimension - 1)
                {
                    if (sumEnergy[i][j] < sumEnergy[i][shortestEndVertex])
                    {
                        shortestEndVertex = j;
                    }
                }
            }
            
        }
        
        int[] seam = new int[parentArrayDimension];
        
        int traverse = shortestEndVertex;
        seam[parentArrayDimension - 1] = traverse;
        
        for (int i = parentArrayDimension - 2; i >= 0; i--)
        {
            traverse = fromPaths[i + 1][traverse];
            seam[i] = traverse;
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
    
    private void seamValidation(int[] seam, boolean isVertical)
    {
        int dimension1;
        int dimension2;
        
        if (isVertical)
        {
            dimension1 = height;
            dimension2 = width;
        }
        else
        {
            dimension1 = width;
            dimension2 = height;
        }
        
        if (seam == null)
        {
            throw new NullPointerException("Must include seam");
        }
        
        if (seam.length != dimension1)
        {
            throw new IllegalArgumentException("Seam length exceeds picture dimension");
        }
        
        for (int i = 0; i < seam.length; i++)
        {
            if (i > 0)
            {
                if (Math.abs(seam[i] - seam[i - 1]) > 1)
                {
                    throw new IllegalArgumentException("Seam is not connected");
                }
            }
            
            if (seam[i] < 0 || seam[i] > dimension2 - 1)
            {
                throw new IllegalArgumentException("Seam must be within the picture");
            }
        }
        
        if (dimension2 == 1)
        {
            throw new IllegalArgumentException("Dimension is already 1 pixel");
        }
    }
    
}

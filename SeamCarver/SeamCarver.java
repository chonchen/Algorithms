import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

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
        
        for (int x = 0; x < width; x++)
        {
            for (int y = seam[x] + 1; y < height; y++)
            {
                picColorValues[x][y - 1] = picColorValues[x][y];
            }
            picColorValues[x][height - 1] = null;
        }
        
        height--;
    }
    
    // remove vertical seam from current picture    
    public void removeVerticalSeam(int[] seam)
    {
        seamValidation(seam, true);
        
        for (int y = 0; y < height; y++)
        {
            for (int x = seam[y] + 1; x < width; x++)
            {
                picColorValues[x - 1][y] = picColorValues[x][y];
                
            }
            picColorValues[width - 1][y] = null;
        }
        
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
        DirectedVertex end = new DirectedVertex(0, 1000);
        
        DirectedVertex[][] energyArray = new DirectedVertex[parentArrayDimension][childArrayDimension];
        
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

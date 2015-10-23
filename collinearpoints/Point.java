import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

/*
 * This class instantiates a point that will be used by Linesegment.java as well as both collinearpoint classes
 */
    
public class Point implements Comparable<Point>
{
    private final int x;
    private final int y;
    
     // constructs the point (x, y)
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
   
    // draws this point
    public void draw()
    {
        //StdDraw.setPenRadius(.01);
        StdDraw.point(x, y);
    }
   
    // draws the line segment from this point to that point
    public void drawTo(Point that)
    {
        //StdDraw.setPenRadius(.001);
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    // string representation
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
    
    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that)
    {
        if (this.y > that.y)
        {
            return 1;
        }
        else if (this.y < that.y)
        {
            return -1;
        }
        else 
        {
            if (this.x > that.x)
            {
                return 1;
            }
            else if (this.x < that.x)
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
    }
    
    // the slope between this point and that point
    public double slopeTo(Point that)
    {
        if (that.y == this.y)
        {
            if (that.x == this.x)
            {
                return Double.NEGATIVE_INFINITY;
            }
            else
            {
                return +0.0;
            }
        }
        else if (that.x == this.x)
        {
            return Double.POSITIVE_INFINITY;
        }
        else
        {
            return (double) (that.y - this.y) / (that.x - this.x);
        }
    }
    
    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder()
    {
        return new PointComparator();
    }
    
    //comparator to be used by slopeOrder() method. It compares the slopes of two lines created by this point and two other points
    private class PointComparator implements Comparator<Point>
    {
        public int compare(Point that1, Point that2)
        {
            if (slopeTo(that1) > slopeTo(that2))
            {
                return 1;
            }
            else if (slopeTo(that1) < slopeTo(that2))
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
        
        //not going to use this interface method
        public boolean equals()
        {
            throw new java.lang.UnsupportedOperationException();
        }
    }
    
}
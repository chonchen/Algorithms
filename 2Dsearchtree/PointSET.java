import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Queue;

public class PointSET
{
    private SET<Point2D> setPoints;
    
    public PointSET()
    {
        setPoints = new SET<Point2D>();
    }
    // construct an empty set of points
    
    public boolean isEmpty()
    {
        return setPoints.isEmpty() == true;
    }
    // is the set empty?
    
    public int size()
    {
        return setPoints.size();
    }
    // number of points in the set
    
    public void insert(Point2D p)
    {
        if (p == null)
        {
            throw new NullPointerException("argument is null");
        }
        
        setPoints.add(p);
    }
    // add the point to the set (if it is not already in the set)
    
    public boolean contains(Point2D p)
    {
        if (p == null)
        {
            throw new NullPointerException("argument is null");
        }
        
        return setPoints.contains(p);
    }
    // does the set contain point p?
    
    public void draw()
    {
        for (Point2D p : setPoints)
        {
            p.draw();
        }
    }
    // draw all points to standard draw
    
    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null)
        {
            throw new NullPointerException("argument is null");
        }
        
        Queue<Point2D> inRectangle = new Queue<Point2D>();
        
        for (Point2D p : setPoints)
        {
            if (rect.contains(p))
            {
                inRectangle.enqueue(p);
            }
        }
        
        return inRectangle;
    }
    // all points that are inside the rectangle
    
    public Point2D nearest(Point2D p)
    {
        if (p == null)
        {
            throw new NullPointerException("argument is null");
        }
        
        Point2D nearestPoint = null;
        
        for (Point2D n : setPoints)
        {
            if (nearestPoint == null)
            {
                nearestPoint = n;
            }
            else if (p.distanceSquaredTo(n) < p.distanceSquaredTo(nearestPoint))
            {
                nearestPoint = n;
            }
        }
        
       return nearestPoint;
    }
    // a nearest neighbor in the set to point p; null if the set is empty 

    public static void main(String[] args)
    {
    }
    // unit testing of the methods (optional) 
}
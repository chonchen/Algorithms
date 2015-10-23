import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Queue;

/*
 * Given a set of points on a graph, this class will tell the user two things
 * 1. What points are contained in a rectangular area specified by the user
 * 2. What point is nearest to a point specified by the user
 */

public class PointSET
{
    private SET<Point2D> setPoints;
    
    // construct an empty set of points
    public PointSET()
    {
        setPoints = new SET<Point2D>();
    }
    
    // is the set empty?
    public boolean isEmpty()
    {
        return setPoints.isEmpty() == true;
    }
    
    // number of points in the set
    public int size()
    {
        return setPoints.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        if (p == null)
        {
            throw new NullPointerException("argument is null");
        }
        
        setPoints.add(p);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p)
    {
        if (p == null)
        {
            throw new NullPointerException("argument is null");
        }
        
        return setPoints.contains(p);
    }
    
    // draw all points to standard draw
    public void draw()
    {
        for (Point2D p : setPoints)
        {
            p.draw();
        }
    }
    
     // all points that are inside the rectangle
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
   
    // a nearest neighbor in the set to point p; null if the set is empty 
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
    
}
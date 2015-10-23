import java.util.Arrays;

public class FastCollinearPoints
{
    private LineSegment[] lines;
    private double[] slopes;
    private Point[] minPoints;
    private int numoflines = 0;
    
    public FastCollinearPoints(Point[] points)
    {
        lines = new LineSegment[1];
        slopes = new double[lines.length];
        minPoints = new Point[lines.length];
        
        if (points == null)
        {
            throw new NullPointerException("Missing Array for Constructor");
        }
        
        Point[] pointscopy = new Point[points.length];
        
        for (int i = 0; i < points.length; i++)
        {
            pointscopy[i] = points[i];
        }
        
        Arrays.sort(pointscopy);
        
        for (int i = 0; i < pointscopy.length; i++)
        {
            if (pointscopy[i] == null)
            {
                throw new NullPointerException("Missing Point");
            }
            else if (i < pointscopy.length - 1 && pointscopy[i].compareTo(pointscopy[i + 1]) == 0)
            {
                throw new IllegalArgumentException("Can't have duplicate points.");
            }
        }
        
        for (int i = 0; i < pointscopy.length - 3; i++)
        {
            Arrays.sort(pointscopy, i + 1, pointscopy.length, pointscopy[i].slopeOrder());
           
            int count = 0;
            
            for (int j = i + 1; j < pointscopy.length; j++)
            {   
                
                if (j < pointscopy.length - 1
                        && pointscopy[i].slopeTo(pointscopy[j]) == pointscopy[i].slopeTo(pointscopy[j + 1]))
                {
                    count++;
                }
                else
                {
                    if (count > 1)
                    {
                        Point[] lineseg = new Point[count + 2];
                        lineseg[0] = pointscopy[i];
                        for (int k = 1; k < lineseg.length; k++)
                        {
                            lineseg[k] = pointscopy[j - count - 1 + k];
                        }
                        Arrays.sort(lineseg);
                        Point min = lineseg[0];
                        Point max = lineseg[lineseg.length - 1];
                        
                        boolean duplicate = false;
                        
                        for (int k = 0; k < numoflines; k++)
                        {
                            if (min.slopeTo(max) == slopes[k])
                            {
                                if (minPoints[k].slopeTo(max) == min.slopeTo(max)
                                        || minPoints[k].slopeTo(max) == Double.NEGATIVE_INFINITY)
                                {
                                    duplicate = true;
                                    break;
                                }
                            }
                        }
                        
                        if (!duplicate)
                        {
                            numoflines++;
                            resize();
                            lines[numoflines - 1] = new LineSegment(min, max);
                            slopes[numoflines - 1] = min.slopeTo(max);
                            minPoints[numoflines - 1] = min;
                        }
                    }
                    
                 count = 0;   
                }
            }
            
        }
    }
    // finds all line segments containing 4 or more points
    
    private void resize()
    {
        if (numoflines > lines.length)
        {
            LineSegment[] temp = new LineSegment[2 * lines.length];
            double[] slopetemp = new double[2 * slopes.length];
            Point[] mintemp = new Point[2 * minPoints.length];
            for (int i = 0; i < lines.length; i++)
            {
                temp[i] = lines[i];
                slopetemp[i] = slopes[i];
                mintemp[i] = minPoints[i];
            }
            lines = temp;
            slopes = slopetemp;
            minPoints = mintemp;
        }
    }
    
    public int numberOfSegments()
    {
        return numoflines;
    }
    // the number of line segments
    
    public LineSegment[] segments()
    {
        LineSegment[] segments = new LineSegment[numoflines];
        for (int i = 0; i < numoflines; i++)
        {
            segments[i] = lines[i];
        }
        
        return segments;
    }
    // the line segments
}
import java.util.Arrays;

/*
 * This class takes in an array of points given by the user. It uses brute force to find all line segments that can be created
 * by four points.
 */

public class BruteCollinearPoints {
    
    private LineSegment[] lines;
    private int numoflines = 0;
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
        lines = new LineSegment[1];
        
        if (points == null)
        {
            throw new NullPointerException("Missing Array for Constructor");
        }
        
        Point[] pointscopy = new Point[points.length];
        
        for (int i = 0; i < pointscopy.length; i++)
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
        
        
        Point[] fourPoints = new Point[4];
        int N = pointscopy.length;
        
        for (int i = 0; i < N; i++)
        {
            for (int j = i + 1; j < N; j++)
            {
                for (int k = j + 1; k < N; k++)
                {
                    if (pointscopy[i].slopeTo(pointscopy[j]) == pointscopy[i].slopeTo(pointscopy[k]))
                    {       
                        for (int l = k + 1; l < N; l++)
                        {   
                            if (pointscopy[i].slopeTo(pointscopy[j]) == pointscopy[i].slopeTo(pointscopy[l]))
                            {
                                numoflines++;
                                resize();
                                fourPoints[0] = pointscopy[i];
                                fourPoints[1] = pointscopy[j];
                                fourPoints[2] = pointscopy[k];
                                fourPoints[3] = pointscopy[l];
                                Arrays.sort(fourPoints);
                                Point min = fourPoints[0];
                                Point max = fourPoints[fourPoints.length - 1];
                                lines[numoflines - 1] = new LineSegment(min, max);
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    // the number of line segments
    public int numberOfSegments()
    {
        return numoflines;
    }
   
    // the line segments
    public LineSegment[] segments()
    {
        LineSegment[] segments = new LineSegment[numoflines];
        for (int i = 0; i < numoflines; i++)
        {
            segments[i] = lines[i];
        }
        
        return segments;
    }
    
    //helper method that resizes the array containing our line segments when it becomes full
    private void resize()
    {
        if (numoflines > lines.length)
        {
            LineSegment[] temp = new LineSegment[2 * lines.length];
            for (int i = 0; i < lines.length; i++)
            {
                temp[i] = lines[i];
            }
            lines = temp;
        }
    }
}
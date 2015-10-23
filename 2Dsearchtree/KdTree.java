import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;

public class KdTree
{
    private class Node implements Comparable<Node>
    {
        private Point2D p;
        private Node left;
        private Node right;
        private boolean isVertical;
        private RectHV rect;
        
        public Node(Point2D p)
        {
            this.p = p;
            //default state is not vertical. Will set this orientation in creation of Node.
            isVertical = false;
        }
        
        public int compareTo(Node that)
        {
            double thisx = this.p.x();
            double thisy = this.p.y();
            double thatx = that.p.x();
            double thaty = that.p.y();
            
            if (this.p.compareTo(that.p) == 0) return 0;
            
            if (this.isVertical)
            {
                if (Double.compare(thisx, thatx) > 0)
                {
                    return 1;
                }
                else
                {
                    return -1;
                }
            }
            else
            {
                if (Double.compare(thisy, thaty) > 0)
                {
                    return 1;
                }
                else
                {
                    return -1;
                }
            }
        }
    }
    
    private Queue<Point2D> allpoints;
    private Queue<Point2D> rectpoints;
    private Node first;
    private Node current;
    private int size = 0;
    private Node nearestN;
    
    public KdTree()
    {
        allpoints = new Queue<Point2D>();
    }
    // construct an empty set of points
    
    public boolean isEmpty()
    {
        return size() == 0;
    }
    // is the set empty?
    
    public int size()
    {
        return size;
    }
    // number of points in the set
    
    public void insert(Point2D p)
    {
        if (p == null)
        {
            throw new NullPointerException("argument is null");
        }
        
        allpoints.enqueue(p);
        
        if (size() == 0)
        {
            first = new Node(p);
            first.isVertical = true;
            first.rect = new RectHV(0, 0, 1, 1);
            size++;
        }
        else
        {
            current = first;
            Node np = new Node(p);
            
            int i = find(np);
            
            if (i > 0)
            {
                current.left = np;
                np.isVertical = !current.isVertical;
                
                if (current.isVertical)
                {
                    np.rect = new RectHV(current.rect.xmin(), current.rect.ymin(), current.p.x(),current.rect.ymax());
                }
                else
                {
                    np.rect = new RectHV(current.rect.xmin(), current.rect.ymin(), current.rect.xmax(),current.p.y());
                }
                
                size++;     
            }
            else if (i < 0)
            {
                current.right = np;
                np.isVertical = !current.isVertical;
                
                if (current.isVertical)
                {
                    np.rect = new RectHV(current.p.x(), current.rect.ymin(), current.rect.xmax(), current.rect.ymax());
                }
                else
                {
                    np.rect = new RectHV(current.rect.xmin(), current.p.y(), current.rect.xmax(), current.rect.ymax());
                }
                
                size++;
            }
           
        }
    }
    // add the point to the set (if it is not already in the set)
    
    public boolean contains(Point2D p)
    {
        if (p == null)
        {
            throw new NullPointerException("argument is null");
        }
        if (size() == 0)
        {
            return false;
        }
        
        current = first;
        
        Node np = new Node(p);
        
        return find(np) == 0;
    }
    // does the set contain point p?
    
    public void draw()
    {
        for (Point2D p : allpoints)
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
        
        rectpoints = new Queue<Point2D>();
        
        current = first;
        
        inRectangle(current, rect);
        
        return rectpoints;
         
    }
    // all points that are inside the rectangle
    
    public Point2D nearest(Point2D p)
    {
        if (p == null)
        {
            throw new NullPointerException("argument is null");
        }
        
        if (size() == 0)
        {
            return null;
        }
        
        Node qn = new Node(p);
        nearestN = first;
        nearestPoint(qn, first);
    
        return nearestN.p;
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    
    private int find(Node np)
    {     
        int comp = current.compareTo(np);
        
        if (comp == 0)
        {
            return 0;
        }
        else if (comp > 0)
        {
            if (current.left == null)
            {
                return 1;
            }
            else
            {
                current = current.left;
                return find(np);
            }
        }
        else
        {
            if (current.right == null)
            {
                return -1;
            }
            else
            {
                current = current.right;
                return find(np);
            }
        }   
    }
    
    private void inRectangle(Node queryNode, RectHV rect)
    {
        if (queryNode != null)
        {
            double queryPointx = queryNode.p.x();
            double queryPointy = queryNode.p.y();
            
            if (rect.contains(queryNode.p))
            {
                rectpoints.enqueue(queryNode.p);
            }
            
            if (queryNode.isVertical == true)
            {
                if (rect.xmin() < queryPointx)
                {
                    inRectangle(queryNode.left, rect);
                }
                
                if (rect.xmax() >= queryPointx)
                {
                    inRectangle(queryNode.right, rect);
                }
            }
            else
            {
                if (rect.ymin() < queryPointy)
                {
                    inRectangle(queryNode.left, rect);
                }
                
                if (rect.ymax() >= queryPointy)
                {
                    inRectangle(queryNode.right, rect);
                }
            }
        }
    }
    
    private void nearestPoint(Node queryNode, Node traverse)
    {
        if (queryNode.p.distanceSquaredTo(traverse.p) < queryNode.p.distanceSquaredTo(nearestN.p))
        {
            nearestN = traverse;
        }
        
        if (traverse.compareTo(queryNode) > 0)
        {
            if (traverse.left != null)
            {
                nearestPoint(queryNode, traverse.left);
            }
            
            if (traverse.right != null)
            {
                if (nearestN.p.distanceSquaredTo(queryNode.p) > traverse.right.rect.distanceSquaredTo(queryNode.p))
                {
                    nearestPoint(queryNode, traverse.right);
                }
            }
        }
        else
        {
            if (traverse.right != null)
            {
                nearestPoint(queryNode, traverse.right);
            }
            
            if (traverse.left != null)
            {
                if (nearestN.p.distanceSquaredTo(queryNode.p) > traverse.left.rect.distanceSquaredTo(queryNode.p))
                {
                    nearestPoint(queryNode, traverse.left);
                }
            }
        }
    }


    public static void main(String[] args)
    {
        KdTree kd = new KdTree();
        
        Point2D one = new Point2D(.50, .50);
        Point2D two = new Point2D(.70, .40);
        Point2D three = new Point2D(.30, .60);
        Point2D four = new Point2D(.20, .10);
        Point2D five = new Point2D(.10, .52);
        Point2D six = new Point2D(.40, .70);
        Point2D seven = new Point2D(.60, .30);
        Point2D eight = new Point2D(1.00, .90);
        Point2D nine = new Point2D(.90, .80);
        
        Point2D query = new Point2D(.05, .65);
        
        
        kd.insert(one);
        kd.insert(two);
        kd.insert(three);
        kd.insert(four);
        kd.insert(five);
        kd.insert(six);
        kd.insert(seven);
        kd.insert(eight);
        kd.insert(nine);
        
        System.out.println(kd.nearest(query).toString());
        
    }
    // unit testing of the methods (optional) 
}
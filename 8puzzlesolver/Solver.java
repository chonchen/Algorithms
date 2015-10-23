import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Solver {
    
    private boolean solvable = false;
    private BoardIterable si;
    private Node goal;
    
    public Solver(Board initial)
    {       
        Node current = new Node(initial, null);
        
        Node twin = new Node(initial.twin(), null);
        
        MinPQ<Node> toprocess = new MinPQ<Node>();
        MinPQ<Node> twinprocess = new MinPQ<Node>();
        
        while (!current.isGoal && !twin.isGoal)
        {  
            for (Board bd : current.nodeboard.neighbors())
            {
                if (current.parent == null || !bd.equals(current.parent.nodeboard))
                    toprocess.insert(new Node(bd, current));
            }
            
            for (Board bd : twin.nodeboard.neighbors())
            {
                if (twin.parent == null || !bd.equals(twin.parent.nodeboard))
                    twinprocess.insert(new Node(bd, twin));
            }
            
            current = toprocess.delMin();
            twin = twinprocess.delMin();
        }
        
        if (current.isGoal)
        {
            solvable = true;
            goal = current;
            si = new BoardIterable();
        }
         
    }
    // find a solution to the initial board (using the A* algorithm)
    
    public boolean isSolvable()
    {
        return solvable;
    }
    // is the initial board solvable?
    
    public int moves()
    {
        if (isSolvable())
        {
            return goal.moves;
        }
        else
        {
            return -1;
        }
    }
    // min number of moves to solve initial board; -1 if unsolvable
    
    public Iterable<Board> solution()
    {
        if (isSolvable())
        {
            return si;
        }
        else
        {
            return null;
        }      
        
    }
    // sequence of boards in a shortest solution; null if unsolvable
    
    private class BoardIterable implements Iterable<Board>
    {
        private Board[] keyarray;
        
        private Node next;
        
        public BoardIterable()
        {
            next = goal;
            keyarray = new Board[goal.moves + 1];
            while (next != null)
            {
                keyarray[next.moves] = next.nodeboard;
                next = next.parent;
            }
            
        }
        
        public Iterator<Board> iterator()
        {
            return new BoardIterator();
        }
        
        private class BoardIterator implements Iterator<Board>
        {
            private int iterated = 0;
            
            public boolean hasNext()
            {
                return iterated < keyarray.length;
            }
            
            public void remove() 
            {
                throw new UnsupportedOperationException("Unsupported.");
            }
            
            public Board next() {
                if (!hasNext())
                {
                    throw new java.util.NoSuchElementException("No next elements.");
                }
                
                Board b = keyarray[iterated];
                iterated++;
                return b;
            }
        }
        
    }
    
    private class Node implements Comparable<Node>
    {
        private Board nodeboard;
        private Node parent;
        private int moves;
        private int manhattan;
        private int priority;
        private boolean isGoal;
        
        public Node(Board nb, Node p)
        {
            nodeboard = nb;
            parent = p;
            if (parent != null)
            {
                moves = p.moves + 1;
            }
            else
            {
                moves = 0;
            }
            manhattan = nodeboard.manhattan();
            if (manhattan == 0)
            {
                isGoal = true;
            }
            priority = manhattan + moves;
        }
        
        public int compareTo(Node that)
        {
            if (this.priority > that.priority)
            {
                return 1;
            }
            else if (this.priority < that.priority)
            {
                return -1;
            }
            else
            {
                if (this.manhattan > that.manhattan)
                {
                    return 1;
                }
                else if (this.manhattan < that.manhattan)
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        }
    }
    
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
            {
                StdOut.println(board);
            }
        }
    }
    // solve a slider puzzle (given below)
}
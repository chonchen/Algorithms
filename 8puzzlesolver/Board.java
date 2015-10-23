import java.util.Iterator;

public class Board
{
    private int[][] board;
    private int N;
    private int izero;
    private int jzero;
        
    public Board(int[][] blocks)
    {
        N = blocks.length;
        
        board = new int[N][N];

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                board[i][j] = blocks[i][j];
                if (board[i][j] == 0)
                {
                    izero = i;
                    jzero = j;
                }
                
            }
        }  
    }
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    
    public int dimension()
    {
        return N;
    }
    // board dimension N
    
    public int hamming()
    {
        int[] hammingarray = new int[N * N];
        int count = 0;
        
         for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (i < N - 1 || j < N - 1)
                {
                    if (board[i][j] == i * N + j + 1)
                    {
                        hammingarray[i * N + j + 1] = 0;
                    }
                    else
                    {
                        hammingarray[i * N + j + 1] = 1;
                    }
                }
            }
         }
        
        for (int i = 1; i < hammingarray.length; i++)
        {
            count = count + hammingarray[i];
        }
        
        return count;
        
    }
    // number of blocks out of place
    
    public int manhattan()
    {
        int[] manhattanarray = new int[N * N];
        
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (board[i][j] > 0)
                {
                    int correctposition_j = (board[i][j] - 1) % N;
                    int correctposition_i = (board[i][j] - 1 - correctposition_j) / N;
                
                    int distance_j = Math.abs(correctposition_j - j);
                    int distance_i = Math.abs(correctposition_i - i);
                
                    manhattanarray[board[i][j]] = distance_i + distance_j;
                }
            }
        }
        
        int count = 0;
        
        for (int i = 1; i < manhattanarray.length; i++)
        {
            count = count + manhattanarray[i];
        }
        
        return count;
        
    }
    // sum of Manhattan distances between blocks and goal
    
    public boolean isGoal()
    {
        return hamming() == 0;
    }
    // is this board the goal board?
    
    public Board twin()
    {
        int i = 0;
        int j = 0;
        
        while (board[i][j] == 0 || board[i][j + 1] == 0)
        {
            i++;
        }
        
        swap(i, j, i, j + 1);
        
        Board twin = new Board(board);
        
        swap(i, j, i, j + 1);
        
        return twin;
        
    }
    // a board that is obtained by exchanging any pair of blocks
    
    
    public boolean equals(Object y)
    {
        if (y == this) return true;
        
        if (y == null) return false;
        
        if (y.getClass() != this.getClass()) return false;
        
        Board that = (Board) y;
        
        String s1 = this.toString();
        String s2 = that.toString();
        
        return s1.equals(s2);
    }
    // does this board equal y?
    
    public Iterable<Board> neighbors()
    {
        BoardIterable bi = new BoardIterable();
        
        if (izero > 0)
        {   
            swap(izero, jzero, izero - 1, jzero);
            
            bi.enqueue(new Board(board));
            
            swap(izero, jzero, izero - 1, jzero);
        }
        
        if (izero < dimension() - 1)
        {
            swap(izero, jzero, izero + 1, jzero);
            
            bi.enqueue(new Board(board));
            
            swap(izero, jzero, izero + 1, jzero);
        }
        
        if (jzero > 0)
        {
            swap(izero, jzero, izero, jzero - 1);
            
            bi.enqueue(new Board(board));
            
            swap(izero, jzero, izero, jzero - 1);
        }
        
        if (jzero < dimension() - 1)
        {
            swap(izero, jzero, izero, jzero + 1);
            
            bi.enqueue(new Board(board));
            
            swap(izero, jzero, izero, jzero + 1);
        }
        
        return bi;
    }
    
    private class BoardIterable implements Iterable<Board>
    {
        private Board[] keyarray = new Board[4];
        private int size = 0;
        
        public void enqueue(Board item)
        {
            if (item == null)
            {
                throw new NullPointerException("Item cannot be null!");
            }
            
            size++;
            
            resize();
            
            keyarray[size - 1] = item;
            
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
                return iterated < size;
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
        
        private void resize()
        {
            if (size > keyarray.length)
            {
                Board[] tempqueue = new Board[2 * keyarray.length];
                for (int i = 0; i < keyarray.length; i++)
                {
                    tempqueue[i] = keyarray[i];
                }
                keyarray = tempqueue;
            }
        }
        
    }
    
    
    // all neighboring boards
    
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++)
        {
            for (int j = 0; j < dimension(); j++)
            {
                s.append(String.format("%2d ", board[i][j]));
            }
        
            s.append("\n");
        }
        
        return s.toString();
        
    }
    // string representation of this board (in the output format specified below)
    
    private void swap(int i1, int j1, int i2, int j2)
    {
        int temp = board[i1][j1];
        board[i1][j1] = board[i2][j2];
        board[i2][j2] = temp;   
    }
    

    public static void main(String[] args)
    {
 
        int[][] a = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
       
        
        Board testboard = new Board(a);
        
        System.out.println(testboard.toString());
        
        Board twin = testboard.twin();
        
        System.out.println(twin.toString());
        
        for (Board b: testboard.neighbors())
        {
            System.out.println(b.toString());
        }
    }
    // unit tests (not graded)
}
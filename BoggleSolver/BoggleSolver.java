import java.util.HashSet;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver
{   
    private DictionaryTrie dictTrie;
    
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
        dictTrie = new DictionaryTrie();
        for (int i = 0; i < dictionary.length; i++)
        {
            dictTrie.add(dictionary[i]);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
        HashSet<String> validWords = new HashSet<String>();
        
        for (int i = 0; i < board.rows(); i++)
        {
            for (int j = 0; j < board.cols(); j++)
            {
                boolean[][] marked = new boolean[board.rows()][board.cols()];
                
                StringBuilder currentString = new StringBuilder();
                
                boggleTraverse(i, j, board, marked, currentString, validWords);
            }
        }
        
        return validWords;
    }
    
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
        if (dictTrie.contains(word))
        {
            return lengthToScore(word.length());
        }
        
        return 0;
    }
    
    private int lengthToScore(int length)
    {
        if (length <= 2)
        {
            return 0;
        }
        else if (length <= 4)
        {
            return 1;
        }
        else if (length == 5)
        {
            return 2;
        }
        else if (length == 6)
        {
            return 3;
        }
        else if (length == 7)
        {
            return 5;
        }
        else
        {
            return 11;
        }
    }
    
    private void boggleTraverse(int row, int col, BoggleBoard board, boolean[][] marked, StringBuilder currentString, HashSet<String> validWords)
    {
        StringBuilder thisString = new StringBuilder(currentString);
        
        boolean[][] thisMarked = new boolean[marked.length][marked[0].length];
        
        for (int i = 0; i < marked.length; i++)
        {
            for (int j = 0; j < marked[0].length; j++)
            {
                thisMarked[i][j] = marked[i][j];
            }
        }
        
        char c = board.getLetter(row, col);
        thisString.append(c);
        if (c == 'Q')
        {
            thisString.append('U');
        }
        thisMarked[row][col] = true;
        
        String s = thisString.toString();
        
        if (s.length() >= 3 && dictTrie.contains(s))
        {
            validWords.add(s);
        }
        
        if (dictTrie.containsPrefix(s))
        {
            for (int i = row - 1; i <= row + 1; i++)
            {
                if (i >= 0 && i < board.rows())
                {
                    for (int j = col - 1; j <= col + 1; j++)
                    {
                        if (j >= 0 && j < board.cols() && !thisMarked[i][j])
                        {
                            boggleTraverse(i, j, board, thisMarked, thisString, validWords);
                        }
                    }
                }
            }   
        }     
    }
    
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
    
}
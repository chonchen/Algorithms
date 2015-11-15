import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    
    private WordNet wordnet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet)
    {
        if (wordnet == null)
        {
            throw new NullPointerException("please include wordnet");
        }
        
        this.wordnet = wordnet;
    }         
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns)
    {
         if (nouns == null)
        {
            throw new NullPointerException("please nouns file");
        }
        
        int longestDistance = 0;
        String outcast = null;
        
        for (int i = 0; i < nouns.length; i++)
        {
            int total = 0;
            
            for (int j = 0; j < nouns.length; j++)
            {
                total = total + wordnet.distance(nouns[i], nouns[j]);
            }
            
            if (total > longestDistance)
            {
                longestDistance = total;
                outcast = nouns[i];
            }
        }
        
        return outcast;
    }  
    // see test client below
    public static void main(String[] args)
    {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
       
        for (int t = 2; t < args.length; t++)
        {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    
    }  
}
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import java.util.ArrayList;
import java.util.Hashtable;

public class WordNet {
    
    private Hashtable<String, ArrayList<Integer>> nounToVertices;
    private ArrayList<String> verticeToSynset;
    private SAP sap;

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)
   {
       if (synsets == null || hypernyms == null)
       {
           throw new NullPointerException("please include synsets and hypernyms");
       }
       
       In iSynsets = new In(synsets);
       In iHypernyms = new In(hypernyms);
       
       nounToVertices = new Hashtable<String, ArrayList<Integer>>();
       verticeToSynset = new ArrayList<String>();
       
       int vertices = 0;
       
       while (iSynsets.hasNextLine())
       {
           String line = iSynsets.readLine();
           String[] parts = line.split(",");
           
           String[] synonyms = parts[1].split(" ");
           for (int i = 0; i < synonyms.length; i++)
           {
               if (!nounToVertices.containsKey(synonyms[i]))
               {
                   nounToVertices.put(synonyms[i], new ArrayList<Integer>());
               }
               
               ArrayList<Integer> homoGraphs = nounToVertices.get(synonyms[i]);
               
               homoGraphs.add(Integer.parseInt(parts[0]));
               
           }
           
           verticeToSynset.add(Integer.parseInt(parts[0]), parts[1]);
           vertices++;
       }
       
       Digraph graph = new Digraph(vertices);
       
       while (iHypernyms.hasNextLine())
       {
           String line = iHypernyms.readLine();
           String[] parts = line.split(",");
           
           for (int i = 1; i < parts.length; i++)
           {
               graph.addEdge(Integer.parseInt(parts[0]), Integer.parseInt(parts[i]));
           }   
       }
       
       int root = 0;
       
       for (int i = 0; i < graph.V(); i++)
       {
           if (graph.outdegree(i) == 0)
           {
               root++;
           }
       }
       
       if (root != 1)
       {
           throw new IllegalArgumentException("graph must have 1 root");
       }
       
       sap = new SAP(graph);
   }

   // returns all WordNet nouns
   public Iterable<String> nouns()
   {   
       return nounToVertices.keySet();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word)
   {
       if (word == null)
       {
           throw new NullPointerException("please include a word");
       }
       
       return nounToVertices.containsKey(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB)
   {
       ArrayList<Integer> v = nounToVertices.get(nounA);
       ArrayList<Integer> w = nounToVertices.get(nounB);
       
       if (v == null || w == null)
       {
           throw new IllegalArgumentException("word not in WordNet");
       }
       
       return sap.length(v, w);
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {
       ArrayList<Integer> v = nounToVertices.get(nounA);
       ArrayList<Integer> w = nounToVertices.get(nounB);
       
       if (v == null || w == null)
       {
           throw new IllegalArgumentException("word not in WordNet");
       }
       
       int index = sap.ancestor(v, w);
       
       if (index > -1)
       {
           return verticeToSynset.get(index);
       }
       return null;
   }

   // do unit testing of this class
   public static void main(String[] args)
   {
       WordNet wordnet = new WordNet(args[0], args[1]);
       System.out.println(wordnet.isNoun("b"));
       /**
       for (String s:wordnet.nouns())
       {
           System.out.println(s);
       }
       **/
       System.out.println(wordnet.distance("potato", "potato_blight"));
       System.out.println(wordnet.sap("potato", "potato_blight"));
       
   }
   
   
}
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    
    Digraph graph;
    Digraph undirected;

   // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
        graph = new Digraph(G);
        undirected = new Digraph(graph.V());
        for (int v = 0; v < undirected.V(); v++)
        {
            for (int w : graph.adj(v))
            {
                undirected.addEdge(v, w);
                undirected.addEdge(w, v);
            }
        }
    }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
        BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(undirected, v);
        
        if (bfdp.hasPathTo(w))
        {
            return bfdp.distTo(w);
        }
        else
        {
            return -1;
        }
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
   {   
       BreadthFirstDirectedPaths bfGraphV = new BreadthFirstDirectedPaths(graph, v);
       BreadthFirstDirectedPaths bfGraphW = new BreadthFirstDirectedPaths(graph, w);
       BreadthFirstDirectedPaths bfUndirected = new BreadthFirstDirectedPaths(undirected, v);
       
       if (bfUndirected.hasPathTo(w))
       {
           for (int i: bfUndirected.pathTo(w))
           {
               if (bfGraphV.hasPathTo(i) && bfGraphW.hasPathTo(i))
               {
                   return i;
               }
           }
       }
      
       return -1;
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
       BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(undirected, v);
       int shortest = Integer.MAX_VALUE;
       
       for (int i: w)
       {
           if (bfdp.hasPathTo(i))
           {
               if (bfdp.distTo(i) < shortest)
               {
                   shortest = bfdp.distTo(i);
               }
           }
       }
       
       if (shortest < Integer.MAX_VALUE)
       {
           return shortest;
       }
       else
       {
           return -1;
       }
       
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   {
       BreadthFirstDirectedPaths bfGraphV = new BreadthFirstDirectedPaths(graph, v);
       BreadthFirstDirectedPaths bfGraphW = new BreadthFirstDirectedPaths(graph, w);
       BreadthFirstDirectedPaths bfUndirected = new BreadthFirstDirectedPaths(undirected, v);
       
       int shortest = Integer.MAX_VALUE;
       int ancestor = -1;
       
       for (int i: w)
       {
           if (bfUndirected.hasPathTo(i))
           {
               for (int j: bfUndirected.pathTo(i))
               {
                   if (bfGraphV.hasPathTo(j) && bfGraphW.hasPathTo(j))
                   {
                       return j;
                   }
               }
           }
       }
       
       return -1;
       
       /**
       for (int i = 0; i < graph.V(); i++)
       {
           if (bfdpv.hasPathTo(i))
           {
               queue.enqueue(i);
           }
       }
       
       int shortest = Integer.MAX_VALUE;
       int ancestor = -1;
       
       for (int i : queue)
       {
           if (bfdpw.hasPathTo(i))
           {
               if (bfdpw.distTo(i) < shortest)
               {
                   shortest = bfdpw.distTo(i);
                   ancestor = i;
               }
           }
       }
       
       return ancestor;
      **/ 
   }

   // do unit testing of this class
   public static void main(String[] args)
   {
       In in = new In(args[0]);
       Digraph G = new Digraph(in);
       SAP sap = new SAP(G);
       while (!StdIn.isEmpty()) {
           int v = StdIn.readInt();
           int w = StdIn.readInt();
           int length   = sap.length(v, w);
           int ancestor = sap.ancestor(v, w);
           StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
       }
       
   }
}
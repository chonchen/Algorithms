import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    
    private Digraph graph;

   // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
        if (G == null)
        {
            throw new NullPointerException("must have digraph");
        }
        
        graph = new Digraph(G);
    }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
       if (v < 0 || w < 0)
       {
           throw new java.lang.IndexOutOfBoundsException("invalid vertices");
       }
       
       Queue<Integer> vQueue = new Queue<Integer>();
       Queue<Integer> wQueue = new Queue<Integer>();
       
       vQueue.enqueue(v);
       wQueue.enqueue(w);
       
       return length(vQueue, wQueue);
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
   {   
       if (v < 0 || w < 0)
       {
           throw new java.lang.IndexOutOfBoundsException("invalid vertices");
       }
       
       Queue<Integer> vQueue = new Queue<Integer>();
       Queue<Integer> wQueue = new Queue<Integer>();
       
       vQueue.enqueue(v);
       wQueue.enqueue(w);
       
       return ancestor(vQueue, wQueue);
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
       if (v == null || w == null)
       {
           throw new NullPointerException("please include both arguments");
       }
       
       BreadthFirstDirectedPaths directedV = new BreadthFirstDirectedPaths(graph, v);
       BreadthFirstDirectedPaths directedW = new BreadthFirstDirectedPaths(graph, w);
       int shortest = Integer.MAX_VALUE;
       
       for (int a: v)
       {
           for (int b: w)
           {
               if (a < 0 || b < 0)
               {
                   throw new java.lang.IndexOutOfBoundsException("invalid vertices");
               }
               
               for (int i = 0; i < graph.V(); i++)
               {
                   if (directedV.hasPathTo(i))
                   {
                       if (directedW.hasPathTo(i))
                       {
                           int distance = directedV.distTo(i) + directedW.distTo(i);
                           
                           if (distance < shortest)
                           {
                               shortest = distance;
                           }
                       }
                       
                   }
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
       if (v == null || w == null)
       {
           throw new NullPointerException("please include both arguments");
       }
       
       BreadthFirstDirectedPaths directedV = new BreadthFirstDirectedPaths(graph, v);
       BreadthFirstDirectedPaths directedW = new BreadthFirstDirectedPaths(graph, w);
       int shortest = Integer.MAX_VALUE;
       int ancestor = -1;
       
       for (int a: v)
       {
           for (int b: w)
           {
               if (a < 0 || b < 0)
               {
                   throw new java.lang.IndexOutOfBoundsException("invalid vertices");
               }
               
               for (int i = 0; i < graph.V(); i++)
               {
                   if (directedV.hasPathTo(i))
                   {
                       if (directedW.hasPathTo(i))
                       {
                           int distance = directedV.distTo(i) + directedW.distTo(i);
                           
                           if (distance < shortest)
                           {
                               shortest = distance;
                               ancestor = i;
                           }
                       }
                   }
               }
           }
       }
       
      return ancestor;
   }

   // do unit testing of this class
   public static void main(String[] args)
   {
       In in = new In(args[0]);
       Digraph G = new Digraph(in);
       SAP sap = new SAP(G);
       while (!StdIn.isEmpty()) {
           int v = StdIn.readInt();
           int a = StdIn.readInt();
           Queue<Integer> queue = new Queue<Integer>();
           queue.enqueue(v);
           queue.enqueue(a);
           int w = StdIn.readInt();
           int b = StdIn.readInt();
           Queue<Integer> queue2 = new Queue<Integer>();
           queue2.enqueue(w);
           queue2.enqueue(b);
           int length   = sap.length(queue, queue2);
           int ancestor = sap.ancestor(queue, queue2);
           StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
       }
       
   }
}
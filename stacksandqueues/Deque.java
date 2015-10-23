import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

/**
 * This class creates a queue where the user can add and delete from both the beginning and end of the queue.
 */

public class Deque<Item> implements Iterable<Item>
{ 
    //node structure for each linked list item the contains pointer to previous node and next node
    private class Node
    {
        private Item item;
        private Node next;
        private Node prev;
    }
    
    private Node first = null;
    private Node last = null;
    
    private int size = 0;
    
    // construct an empty deque
    public Deque()
    {
       
    }
    
    // is the deque empty?
    public boolean isEmpty()
    {
        return first == null;
    }
    
    // return the number of items on the deque
    public int size()
    {
        return size;
    }
    
    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException("Item cannot be null!");
        }
        
       size++;
       Node oldfirst = first;
       first = new Node();
       first.item = item;
       first.next = oldfirst;
       if (first.next == null){
           last = first;
       }
       else 
       {
           oldfirst.prev = first;
       }
    }
    
    // add the item to the end
    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException("Item cannot be null!");
        }
        
       size++;
       Node oldlast = last;
       last = new Node();
       last.item = item;
       last.prev = oldlast;
       if (last.prev == null) {
           first = last;
       }
       else
       {
           oldlast.next = last;
       }
    }           
    
    // remove and return the item from the front
    public Item removeFirst()
    {
       if (isEmpty())
       {
           throw new  java.util.NoSuchElementException("Deque is already empty!");
       }
      
       size--;
       
       Item item = first.item;
       first = first.next;
       
       if (isEmpty())
       {
           last = null;
       }
       else
       {
           first.prev = null;
       }
       
       return item;  
   
    }
    
    // remove and return the item from the end
    public Item removeLast()
    {
        
       if (isEmpty())
       {
           throw new  java.util.NoSuchElementException("Deque is already empty!");
       }
       
       size--;
       
       Item item = last.item;
       last = last.prev;
       
       if (last == null)
       {
           first = null;
       }
       else
       {
           last.next = null;
       }
       
        return item;   
    }                
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator()
    {    
        
        return new ListIterator();
             
    }        
    
    //iterator to be used by the iterator() method
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() 
        {
            throw new UnsupportedOperationException("Unsupported.");
        }
        
        public Item next() {
            if (!hasNext())
            {
                throw new java.util.NoSuchElementException("No next elements.");
            }
            
            Item item = current.item;
            current = current.next;
            return item;
        }
    
    }
    
    // unit testing
    public static void main(String[] args)
    {
        String test = "Hello";
        String test2 = "World";
        String test3 = "!!!!";
        
        Deque<String> s = new Deque<String>();
        
        s.addFirst(test);
        s.addLast(test2);
        s.addFirst(test3);
        
        for (String d : s)
            StdOut.println(d);
        
        StdOut.println(s.size());
        
        s.removeFirst();
        for (String d : s)
            StdOut.println(d);
        
        StdOut.println(s.size());
        
        s.removeLast(); 
        for (String d : s)
            StdOut.println(d);
        
        StdOut.println(s.size());
        
        s.removeLast();
        for (String d : s)
            StdOut.println(d);
        
        StdOut.println(s.size());
        
        s.removeFirst();
        for (String d : s)
            StdOut.println(d);
        
        StdOut.println(s.size());
    
    }  
}
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

/**
 * This class creates a queue that dequeues a random list item to the user. It uses an array instead of a linked list for better
 * performance in the randomized dequeue.
 **/

public class RandomizedQueue<Item> implements Iterable<Item>
{
   
   private Item[] queue;
   private int size = 0;
    
   // construct an empty randomized queue
   public RandomizedQueue()
   {
      queue = (Item[]) new Object[1];
   }
   
   // is the queue empty?
   public boolean isEmpty()
   {
       return size() == 0;
   }
   
   // return the number of items on the queue
   public int size()
   {
       return size;
   }
   
   // add the item
   public void enqueue(Item item)
   {
       if (item == null)
        {
            throw new NullPointerException("Item cannot be null!");
        }
        
       size++;
       
       if (size > queue.length)
       {
           Item[] newqueue = (Item[]) new Object[2 * queue.length];
           for (int i = 0; i < queue.length; i++)
           {
               newqueue[i] = queue[i];
           }
           queue = newqueue;
       }
       
       queue[size - 1] = item;
   }
   
   // remove and return a random item
   public Item dequeue()
   {
       if (isEmpty())
       {
           throw new  java.util.NoSuchElementException("Queue is already empty!");
       }
       
       int rand = StdRandom.uniform(size);
       
       Item item = queue[rand];
       
       if (rand != size - 1) 
       {
           queue[rand] = queue[size - 1];
       }
       
       queue[size - 1] = null;
       
       size--;
       
       if (size > 0 && size <= queue.length / 4)
       {
           Item[] newqueue = (Item[]) new Object[queue.length / 2];
           int count = 0;
           
           for (int i = 0; i < queue.length; i++)
           {
               if (queue[i] != null)
               {
                   newqueue[count] = queue[i];
                   count++;
               }
           }
           queue = newqueue;
       }     
       
       return item;
       
   }
   
   // return (but do not remove) a random item
   public Item sample()
   {
       if (isEmpty())
       {
           throw new  java.util.NoSuchElementException("Queue is empty!");
       }
       
       int rand = 0;
       
       do
       {
           rand = StdRandom.uniform(queue.length);
       }
       while(queue[rand] == null);
       
       Item item = queue[rand];
       
       return item;
   }
   
   // return an independent iterator over items in random order
   public Iterator<Item> iterator()
   {
       return new RandomIterator();
   }
   
   //the iterator that iterator() method will use
   private class RandomIterator implements Iterator<Item> {
        private int iterated = 0;
        private Item[] shuffle;
        
        public RandomIterator()
        {
            int count = 0;
            shuffle = (Item[]) new Object[size];
        
            for (int i = 0; i < queue.length; i++)
            {
                if (queue[i] != null)
                {
                    shuffle[count] = queue[i];
                    count++;
                }
            }
        
            StdRandom.shuffle(shuffle);
        }
        
        public boolean hasNext() {
            return iterated < size;
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
            
            Item item = shuffle[iterated];
            iterated++;
            return item;
        }
    
    }
   
   // unit testing
   public static void main(String[] args)
   {
       RandomizedQueue<String> rq = new RandomizedQueue<String>();
       rq.enqueue("Hello");
       rq.enqueue("World");
       rq.enqueue("My Random");
       
       for (String s : rq)
            StdOut.println(s);
       
       rq.dequeue();
       
       for (String s : rq)
            StdOut.println(s);
   
   }
}
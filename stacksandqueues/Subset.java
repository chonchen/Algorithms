import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * This class is used to test the randomized queue. Run the function and enter a list of items to be enqueued. The program will then
 * dequeue these items in random order
 */

public class Subset
{
    public static void main(String[] args)
    {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
        {    
            String input = StdIn.readString();
            rq.enqueue(input);
        }
        
        for (int i = 0; i < k; i++)
        {
            StdOut.println(rq.dequeue());
        }
    }
}
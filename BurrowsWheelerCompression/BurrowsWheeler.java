import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;

public class BurrowsWheeler
{
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode()
    {
        String s = BinaryStdIn.readString();
        char[] charArray = new char[s.length()];
        CircularSuffixArray csa = new CircularSuffixArray(s);
        
        int first = 0;
        
        for (int i = 0; i < s.length(); i++)
        {
            int index = csa.index(i);
            
            if (index == 0)
            {
                charArray[i] = s.charAt(s.length() - 1);
                first = i;
            }
            else
            {
                charArray[i] = s.charAt(index - 1);
            }
        }
        
        String transform = new String(charArray);
        
        BinaryStdOut.write(first);
        BinaryStdOut.write(transform);
        BinaryStdOut.close();
        
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode()
    {
        int first = BinaryStdIn.readInt();
        String transform = BinaryStdIn.readString();
        
        char[] firstLetters = transform.toCharArray();
        int[] next = new int[transform.length()];
        char[] decodedChars = new char[transform.length()];
        
        Hashtable<Character, LinkedList<Integer>> hashtable = new Hashtable<Character, LinkedList<Integer>>();
        for (int i = 0; i < transform.length(); i++)
        {
            if (!hashtable.containsKey(transform.charAt(i)))
            {
                hashtable.put(transform.charAt(i), new LinkedList<Integer>());
            }
            LinkedList<Integer> addIndex = hashtable.get(transform.charAt(i));
            addIndex.add(i);
        }

        Arrays.sort(firstLetters);
        
        for (int i = 0; i < firstLetters.length; i++)
        {
            LinkedList<Integer> removeIndex = hashtable.get(firstLetters[i]);
            
            int j = removeIndex.remove();
            
            if (i == j)
            {
                if (removeIndex.size() > 0)
                {
                    int temp = j;
                    j = removeIndex.remove();
                    removeIndex.addFirst(temp);
                }
            }
            
            next[i] = j;
        }
        
        int nextIndex = first;
        for (int i = 0; i < next.length; i++)
        {
            decodedChars[i] = firstLetters[nextIndex];
            nextIndex = next[nextIndex];
        }
        
        BinaryStdOut.write(new String(decodedChars));
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args)
    {   
        if (args[0].compareTo("-") == 0)
        {
            encode();
        }
        else if (args[0].compareTo("+") == 0)
        {
            decode();
        }
    }
}
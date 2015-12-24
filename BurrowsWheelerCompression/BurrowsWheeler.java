import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

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
        
        int[] next = new int[transform.length()];
        char[] decodedChars = new char[transform.length()];
        
        int[] count = new int[256 + 1];
              
        for (int i = 0; i < transform.length(); i++)
        {
            count[transform.charAt(i) + 1]++;
        }
        
        for (int i = 0; i < count.length - 1; i++)
        {
            count[i + 1] += count[i];
        }
        
        for (int i = 0; i < transform.length(); i++)
        {
            next[count[transform.charAt(i)]++] = i;
        }
        
        
        int nextIndex = first;
        for (int i = 0; i < next.length; i++)
        {
            decodedChars[i] = transform.charAt(next[nextIndex]);
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
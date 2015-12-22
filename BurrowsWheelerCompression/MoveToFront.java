import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.util.LinkedList;

public class MoveToFront
{
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode()
    {
        LinkedList<Character> sequence = new LinkedList<Character>();
        
        for (char i = 0; i < 256; i++)
        {
            sequence.addLast(i);
        }
        
        while (!BinaryStdIn.isEmpty())
        {
            char c = BinaryStdIn.readChar();
            int indexOfC = sequence.indexOf(c);
            BinaryStdOut.write((char) indexOfC);
            
            sequence.remove(indexOfC);
            sequence.addFirst(c);
        }
        
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode()
    {
        LinkedList<Character> sequence = new LinkedList<Character>();
        
        for (char i = 0; i < 256; i++)
        {
            sequence.addLast(i);
        }
        
        while (!BinaryStdIn.isEmpty())
        {
            int indexOfC = (int) BinaryStdIn.readChar();
            char c = sequence.remove(indexOfC);
            BinaryStdOut.write(c);
            
            sequence.addFirst(c);
        }
        
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
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
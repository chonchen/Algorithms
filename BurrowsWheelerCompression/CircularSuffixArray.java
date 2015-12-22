import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray
{   
    private int inputLength;
    private Integer[] indexes;
    
    // circular suffix array of s
    public CircularSuffixArray(String s)
    {
        if (s == null)
        {
            throw new NullPointerException("Please enter a string");
        }
        
        inputLength = s.length();
        indexes = new Integer[inputLength];
        char[] charArrayOfS = new char[inputLength];
        
        for (int i = 0; i < inputLength; i++)
        {
            indexes[i] = i;
            charArrayOfS[i] = s.charAt(i);
        }
     
        Arrays.sort(indexes, new IndexComparator(charArrayOfS));
    }
    
    // length of s
    public int length()
    {
        return inputLength;
    }
    
    // returns index of ith sorted suffix
    public int index(int i)
    {
        if (i < 0 || i > inputLength - 1)
        {
            throw new IndexOutOfBoundsException("Please enter proper index");
        }
        
        return indexes[i];
    }
    
    private class IndexComparator implements Comparator<Integer>
    {
        private char[] charArray;
            
        public IndexComparator(char[] charArray)
        {
            this.charArray = charArray;
        }
        
        public int compare(Integer indexInput1, Integer indexInput2)
        {
            int index1 = indexInput1;
            int index2 = indexInput2;
            
            for (int i = 0; i < charArray.length; i++)
            {
                if (charArray[index1] > charArray[index2])
                {
                    return 1;
                }
                else if (charArray[index1] < charArray[index2])
                {
                    return -1;
                }
                else
                {
                    if (index1 == charArray.length - 1)
                    {
                        index1 = 0;
                    }
                    else
                    {
                        index1++;
                    }
                    
                    if (index2 == charArray.length - 1)
                    {
                        index2 = 0;
                    }
                    else
                    {
                        index2++;
                    }                    
                }
            }
            return 0;
        }
        
    }
    
    // unit testing of the methods
    public static void main(String[] args)
    {
        String word = "ABRACADABRA!";
        CircularSuffixArray hey = new CircularSuffixArray(word);
        
        for (int i = 0; i < word.length(); i++)
        {
            System.out.println(hey.index(i));
        }  
    }
}
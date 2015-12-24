public class CircularSuffixArray
{   
    private int inputLength;
    private int[] indexes;
    
    // circular suffix array of s
    public CircularSuffixArray(String s)
    {
        if (s == null)
        {
            throw new NullPointerException("Please enter a string");
        }
        
        inputLength = s.length();
        indexes = new int[inputLength];
        
        for (int i = 0; i < inputLength; i++)
        {
            indexes[i] = i;
        }
        
        int[] aux = new int[indexes.length];
        
        sort(s, indexes, 0, s.length() - 1, 0, aux);
     
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
    
    private void sort(String a, int[] index, int lo, int hi, int charIndex, int[] aux)
    {
        
        if (charIndex >= a.length())
        {
            return;
        }
        
        if (hi <= lo + 15)
        {
            insertSort(a, index, lo, hi, charIndex);
            return;
        }
        
        int[] count = new int[256 + 2];
        
        for (int i = lo; i <= hi; i++)
        {
            count[a.charAt((index[i] + charIndex) % a.length()) + 2]++;
        }
        
        for (int i = 0; i < count.length - 1; i++)
        {
            count[i + 1] += count[i];
        }
        
        for (int i = lo; i <= hi; i++)
        {
            aux[count[a.charAt((index[i] + charIndex) % a.length()) + 1]++] = index[i];
        }
        
        for (int i = lo; i <= hi; i++)
        {
            index[i] = aux[i - lo];
        }
        
        for (int i = 0; i < 256; i++)
        {
            sort(a, index, lo + count[i], lo + count[i + 1] - 1, charIndex + 1, aux);
        }
        
    }
    
    private void insertSort(String a, int[] index, int lo, int hi, int charIndex)
    {
        for (int i = lo; i <= hi; i++)
        {
            for (int j = i; j > lo && less(a, index[j], index[j-1], charIndex); j--)
            {
                exchange(index, j, j-1);
            }
        }
    }
    
    private void exchange(int[] index, int i, int j)
    {
        int temp = index[i];
        index[i] = index[j];
        index[j] = temp;
    }
    
    private boolean less(String a, int indexValue1, int indexValue2, int charIndex)
    {
        for (int i = charIndex % a.length(); i < a.length(); i++)
        {
            if (a.charAt((indexValue1 + i) % a.length()) < a.charAt((indexValue2 + i) % a.length()))
            {
                return true;
            }
            
            if (a.charAt((indexValue1 + i) % a.length()) > a.charAt((indexValue2 + i) % a.length()))
            {
                return false;
            }
        }
        return false;
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
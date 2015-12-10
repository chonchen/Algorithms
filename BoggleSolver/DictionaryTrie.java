public class DictionaryTrie 
{
    private class Node
    {
        private boolean isWord;
        private Node[] next = new Node[26];
    }
    
    private Node[] firstLetter;
    
    public DictionaryTrie()
    {
        firstLetter = new Node[26];
    }
    
    public void add(String word)
    {
        verifyInput(word);
        addHelper(word, 0, firstLetter);
    }
    
    public boolean contains(String word)
    {
        verifyInput(word);
        
        Node n = containsHelper(word, 0, firstLetter);
        if (n != null)
        {
            return n.isWord;
        }
        return false;
    }
    
    public boolean containsPrefix(String prefix)
    {
        verifyInput(prefix);
        
        Node n = containsHelper(prefix, 0, firstLetter);
        if (n != null)
        {
            return true;
        }
        return false;
    }
    
     private void addHelper(String word, int currentCharPosition, Node[] currentChar)
    {
        int charToInteger = Character.toUpperCase(word.charAt(currentCharPosition)) - 'A';
        
        if (currentChar[charToInteger] == null)
        {
            currentChar[charToInteger] = new Node();
        }
        
        if (currentCharPosition < word.length() - 1)
        {
            addHelper(word, currentCharPosition + 1, currentChar[charToInteger].next);
        }
        else
        {
            currentChar[charToInteger].isWord = true;
        }
    }
    
    private Node containsHelper(String word, int currentCharPosition, Node[] currentChar)
    {
        int charToInteger = Character.toUpperCase(word.charAt(currentCharPosition)) - 'A';
        
        if (currentCharPosition < word.length() - 1)
        {
            if (currentChar[charToInteger] == null)
            {
                return null;
            }
            else
            {
                return containsHelper(word, currentCharPosition + 1, currentChar[charToInteger].next);
            }
        }
        
        return currentChar[charToInteger];
    }
    
    private void verifyInput(String input)
    {
        if (input == null)
        {
            throw new NullPointerException("Please enter a word");
        }
        
        for (int i = 0; i < input.length(); i++)
        {
            if (!Character.isLetter(input.charAt(i)))
            {
                throw new IllegalArgumentException("Word must be all characters");
            }
        }
    }
}

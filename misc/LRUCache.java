import java.util.Hashtable;  
  
public class LRUCache {  
      
    private class Node  
    {  
        int key;  
        int value;  
        Node prev;  
        Node next;  
          
        public Node(int key, int value)  
        {  
            this.key = key;  
            this.value = value;  
        }  
    }  
      
    Node first = null;  
    Node last = null;  
    private int capacity;  
    private int size = 0;  
    private Hashtable<Integer, Node> findNode;  
      
    public LRUCache(int capacity) {  
        this.capacity = capacity;  
        findNode = new Hashtable<Integer, Node>();  
    }  
      
    public int get(int key) {  
          
        Node found = retrieveNode(key);  
          
        if (found != null)  
        {  
            return found.value;  
        }  
          
        return -1;  
    }  
      
    public void set(int key, int value) {  
          
        Node found = retrieveNode(key);  
          
        if (found != null)  
        {  
            found.value = value;  
        }  
        else  
        {  
            size++;  
              
            checkCapacity();  
              
            Node entry = new Node(key, value);  
              
            entry.next = first;  
              
            if (first != null)  
            {  
                first.prev = entry;  
            }  
            else  
            {  
                last = entry; 
            }  
              
            first = entry;  
              
            findNode.put(key, entry);  
        }  
          
    }  
      
    private Node retrieveNode(int key)  
    {  
        Node found = findNode.get(key); 
         
        if (found != null)  
        {  
            if (found == first) 
            { 
                return found; 
            } 
             
            if (found == last) 
            { 
                last = last.prev; 
            } 
            else 
            { 
                found.next.prev = found.prev; 
            } 
 
            found.prev.next = found.next;  
              
            found.prev = null;  
              
            found.next = first;  
            first.prev = found;  
            first = found;  
        }  
          
        return found;  
    }  
      
    private void checkCapacity()  
    {  
        if (size > capacity)  
        {  
            Node toDelete = last; 
              
            if (last.prev != null)  
            {  
                last = last.prev;  
                last.next = null;  
            }  
              
            findNode.remove(toDelete.key); 
              
            size--;  
        }  
    } 
}
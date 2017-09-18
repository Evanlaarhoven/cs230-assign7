//********************************************************************
//  CircularArrayQueue.java       Java Foundations
//
//  Represents an array implementation of a queue in which neither
//  end of the queue is fixed in the array. The variables storing the
//  indexes of the front and rear of the queue continually increment
//  as elements are removed and added, cycling back to 0 when they
//  reach the end of the array.
//********************************************************************

package javafoundations;

import javafoundations.exceptions.*;

public class CircularArrayQueue<T> implements Queue<T>
{
   private final int DEFAULT_CAPACITY = 10;
   private int front, rear, count;
   private T[] queue;

   public CircularArrayQueue()
   {
      front = rear = count = 0;
      queue = (T[]) (new Object[DEFAULT_CAPACITY]);
   }

   public void enqueue (T element)
   {
      if (count == queue.length)
         expandCapacity();

      queue[rear] = element;
      rear = (rear+1) % queue.length;
      count++;
   }

   //-----------------------------------------------------------------
   //  Creates a new array to store the contents of this queue with
   //  twice the capacity of the old one.
   //-----------------------------------------------------------------
   public void expandCapacity()
   {
      T[] larger = (T[])(new Object[queue.length*2]);

      for (int index=0; index < count; index++)
         larger[index] = queue[(front+index) % queue.length];

      front = 0;
      rear = count;
      queue = larger;
   }

   //-----------------------------------------------------------------
   //  The following methods are left as Programming Projects.
   //-----------------------------------------------------------------
    public T dequeue () throws EmptyCollectionException {
      if(count == 0)
        throw new EmptyCollectionException("Dequeue operation failed. "
            + "The queue is empty.");
      
      T element = queue[front];
      // Fixed on 11/2/16 by Sohie
      // front = (front-1) % queue.length;
      front = (front + 1) % queue.length;
      count--;
      
      return element;
    }
    
    public T first () throws EmptyCollectionException {
    if(count == 0)
        throw new EmptyCollectionException("First operation failed. "
            + "The queue is empty.");
      
      return queue[front];
    }
    
    public int size() {
      return count;
    }
    
    public boolean isEmpty() {
      return count == 0;
    }
    
    public String toString() {
       String result = "<front of queue>\n";

 // Fixed on 11/4/16 by Christine
       //for(int index = front; index < count; index++)
 for(int index = front; index < count+front; index++)
         result += queue[index%queue.length] + "\n";
       
       return result;
    }
}
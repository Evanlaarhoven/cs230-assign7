//********************************************************************
//  LinkedQueue.java       Java Foundations
//
//  Represents a linked implementation of a queue.
//********************************************************************

package javafoundations;

import javafoundations.exceptions.*;

public class LinkedQueue<T> implements Queue<T> {
 private int count;
 private LinearNode<T> front, rear;

 //-----------------------------------------------------------------
 //  Creates an empty queue.
 //-----------------------------------------------------------------
 public LinkedQueue() {
  count = 0;
  front = rear = null;
 }

 //-----------------------------------------------------------------
 //  Adds the specified element to the rear of this queue.
 //-----------------------------------------------------------------
 public void enqueue (T el) {
   //create a LinearNote out of the input element
  LinearNode<T> node = new LinearNode<T>(el);

  if (count == 0)
   front = node;
  else
   rear.setNext(node);

  rear = node;
  count++;
 }

 //-----------------------------------------------------------------
 //  The following methods are left as Programming Projects.
 //-----------------------------------------------------------------
 public T dequeue () throws EmptyCollectionException {
  
 }
 
 public T first () throws EmptyCollectionException {
  
 }
 
 
 public boolean isEmpty() {
   
 }
 
 public int size() {
  
 }
 
 public String toString() {

 }
 
 public static void main(String[] args) {

 }
 
}

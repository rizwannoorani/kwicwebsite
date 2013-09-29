/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Data structure to hold objects within a queue.
 *                    Code influenced by example 1 project from syllabus.
 *  File              LinkedQueue.java
 *  Date:             9/23/2013
********************************************************************/

import java.util.*;
import java.io.*;

public class LinkedQueue extends LinkedList implements Serializable
{
    int intSize = 0; //the number of objects in the queue

    //return number of objects in the queue
    public int getSize()
    {
        return intSize;
    }

    //return if the queue is empty
    @Override
    public boolean isEmpty()
    {
        return (intSize==0);
    }

    //return object at the front
    public Object getFront()
    {
        return getFirst();
    }

    //return object at the tail
    public Object getTail()
    {
        return getLast();
    }

    //add object to the tail
    public void  enqueue(Object obj)
    {
        intSize++;
        addLast(obj);
    }

    //remove object from the front
    public Object dequeue()
    {
        intSize--;
        return removeFirst();
    }

    //return the objects within the queue seperated by a space as a String
    @Override
    public String toString()
    {
        String s = new String();

        for(int i=0; i< this.size(); i++)
        {
            String temp = (String)this.dequeue();
            s+=(temp).toString()+" ";
            this.enqueue(temp);
        }

        return s;
    }
}
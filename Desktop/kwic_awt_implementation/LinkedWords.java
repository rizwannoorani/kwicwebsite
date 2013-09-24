/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Data structure to hold words within a line.
 *                    Code influenced by example 1 project from syllabus.
 *  File              LinkedWords.java
 *  Date:             9/23/2013
********************************************************************/

import java.util.*;
import java.io.*;

public class LinkedWords extends LinkedList implements Serializable
{
    int intSize = 0; //the number of words in the queue

    //return number of words in the queue
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

    //return word at the front
    public Object getFront()
    {
        return getFirst();
    }

    //return word at the tail
    public Object getTail()
    {
        return getLast();
    }

    //add word at the tail
    public void  enqueue(Object obj)
    {
        intSize++;
        addLast(obj);
    }

    //remove word from the front
    public Object dequeue()
    {
        intSize--;
        return removeFirst();
    }

    //return the words within the queue in a String
    @Override
    public String toString()
    {
        String s = new String();

        for(int i=0; i< this.size(); i++)
        {
            StorageLine temp = (StorageLine)this.dequeue();
            s+=(temp).toString()+" ";
            this.enqueue(temp);
        }

        return s;
    }
}
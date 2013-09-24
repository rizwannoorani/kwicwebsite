/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Object to alphabetize and merge the lines.
 *                    Code influenced by example 1 project from syllabus.
 *  File              Alphabetizer.java
 *  Date:             9/23/2013
********************************************************************/

import java.io.*;

public class Alphabetizer extends Thread implements Serializable
{
    private Kwic            kwicRef;
    private LinkedWords     arShiftedLines;

    // Create Alphabetizer object from the passed lines
    public Alphabetizer(Kwic k, String n, LinkedWords q)
    {
        super(n);
        kwicRef = k;
        arShiftedLines = q;
        start();
    }

    @Override
    public void run()
    {

        mergeSort(arShiftedLines);
/*
 * Code to merge the previous indexed lines
 * to the newly indexed lines
        if(newlineQueue.size()==0)
        {
            newlineQueue=addedLines;
        }
        else
        {
            LinkedQueue resultQueue=new LinkedQueue();
            merge(newlineQueue, addedLines,  resultQueue);

            newlineQueue=resultQueue;
            resultQueue = null;
        }
*/
        displayMerge();
    }

    //merge sort  function
    public void mergeSort(LinkedWords Q)
    {
        int n = Q.getSize();
        // a sequence with 0 or 1 element is already sorted
        if (n<2) return;
        // divid
        int i = n;

        LinkedWords Q1 = new LinkedWords();

        do{
            Q1.enqueue(Q.dequeue());
            i--;
        }
        while(i>n/2);

        LinkedWords Q2=new LinkedWords();

        do{
            Q2.enqueue(Q.dequeue());
            i--;
        }
        while(i>0);

        //recursion
        mergeSort(Q1);
        mergeSort(Q2);
        //conquer
        merge(Q1,Q2,Q);
    }

    //merge the two sets of queue in a new queue
    public void merge(LinkedWords Q1, LinkedWords Q2, LinkedWords Q)
    {
        while(!Q1.isEmpty()&&!Q2.isEmpty())
        {
            String s1 = (String)Q1.getFront().toString();
            int startPosition1 = s1.lastIndexOf(" ");
            s1= s1.substring(0, startPosition1-1);

            String s2 = (String)Q2.getFront().toString();
            int startPosition2 = s2.lastIndexOf(" ");
            s2 = s2.substring(0, startPosition2-1);

            if(s1.compareTo(s2)<=0)
            Q.enqueue(Q1.dequeue());

            else
            Q.enqueue(Q2.dequeue());
        }
        while(!Q1.isEmpty())
        {
            Q.enqueue(Q1.dequeue());
        }

        while(!Q2.isEmpty())
        {
            Q.enqueue(Q2.dequeue());
        }
    }

    private void displayMerge()
    {
        Line    lneTemp;
        String  strLine = new String();

        for (int i=0; i<arShiftedLines.getSize(); i++)
        {
            // reset the line
            strLine = "";
            strLine = (String)arShiftedLines.dequeue().toString();
            lneTemp = new Line(strLine);
            arShiftedLines.enqueue(lneTemp);
            kwicRef.displayMerge(this.getName() + " : " + strLine);
        }        
    }
}
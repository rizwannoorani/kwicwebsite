/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Object to merge the lines.
 *                    Code influenced by example 1 project from syllabus.
 *  File:             MergerComponent.java
 *  Date:             9/23/2013
********************************************************************/

public class MergerComponent implements MergerInterface
{
    private OutputComponent outputRef;
    private LinkedQueue     queSortedLines;

    // Create MergerComponent object with output reference
    public MergerComponent(OutputComponent o)
    {
        outputRef = o;
    }

    // Interface - add the alphabetized lines to the indexed queue
    public void setData(LinkedQueue q)
    {
        // set the alphabetized lines
        queSortedLines = q;

        // get the previously indexed lines
        LinkedQueue queIndexedQueue = new LinkedQueue();
        queIndexedQueue = outputRef.getIndex();

        // create a queue to store the sorted results
        LinkedQueue queResultQueue=new LinkedQueue();

        // if the indexed queue is empty then
        // set the result queue equal to the sorted lines
        if(queIndexedQueue.getSize()==0)
        {
            queResultQueue=queSortedLines;
        }
        else
        {            
            merge(queIndexedQueue, queSortedLines,  queResultQueue);
        }

        // save the result queuea as the indexed queue
        outputRef.setData(queResultQueue);
        queResultQueue = null;
    }

    //merge sort function
    private void mergeSort(LinkedQueue Q)
    {
        int n = Q.getSize();
        // a sequence with 0 or 1 element is already sorted
        if (n<2) return;
        // divide
        int i = n;

        LinkedQueue Q1 = new LinkedQueue();

        do{
            Q1.enqueue(Q.dequeue());
            i--;
        }
        while(i>n/2);

        LinkedQueue Q2=new LinkedQueue();

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
    private void merge(LinkedQueue Q1, LinkedQueue Q2, LinkedQueue Q)
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
}
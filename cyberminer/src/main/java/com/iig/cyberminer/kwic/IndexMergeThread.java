/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Thread to merge input lines and the existing Index.
 *  File              IndexMergeThread.java
 *  Date:             10/07/2013
********************************************************************/
package com.iig.cyberminer.kwic;

public class IndexMergeThread implements Runnable
{
    static int intThreadCount = 0;
    int intThreadID;
    private MergerComponent MC;
    private OutputComponent outputRef;
    private LinkedQueue queSortedLines;

    // Create an IndexMergeThread with the MergerComponent reference
    public IndexMergeThread(MergerComponent mc, OutputComponent o)
    {
        intThreadID = ++intThreadCount;
        MC = mc;
        outputRef = o;
    }

    // Interface - merge the lines from the alphabetizer with the Index
    public void run()
    {
        if (MC.intQueueSize < 1) {
//System.out.println("IndexMerge Thread should not be here: " + MC.intQueueSize);
            return;
        }
        // the deque here will impact the input queue
        // owned by the MergerComponent
        queSortedLines = MC.dequeue();

        // get the existing indexed lines
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
            MergeSort.merge(queIndexedQueue, queSortedLines,  queResultQueue);
        }

/**************** begin debugging output *********************/
        System.out.println("IndexMerge thread: " + intThreadID);
/**************** end debugging output ***********************/

        // tell the MergerComponent that the process is done
        MC.processComplete(queResultQueue);
        queResultQueue = null;
    }
}
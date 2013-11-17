/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Thread to merge multiple input lines.
 *  File              MergerThread.java
 *  Date:             10/07/2013
********************************************************************/
package com.iig.cyberminer.kwic;

public class MergerThread implements Runnable
{
    static int intThreadCount = 0;
    int intThreadID;
    private MergerComponent MC;
    private OutputComponent outputRef;
    private LinkedQueue queSortedLines1;
    private LinkedQueue queSortedLines2;

    // Create a MergerThread with the MergerComponent reference
    public MergerThread(MergerComponent mc, OutputComponent o)
    {
        intThreadID = ++intThreadCount;
        MC = mc;
        outputRef = o;
    }

    // Interface - merge the lines from the alphabetizer
    public void run()
    {

/**************** begin debugging output *********************/
        System.out.println("Merger thread: " + intThreadID);
/**************** end debugging output ***********************/

        // create a queue to store the sorted results
        LinkedQueue queResultQueue=new LinkedQueue();

        if (MC.intQueueSize <= 1) {
//System.out.println("Merger Thread should not be here: " + MC.intQueueSize);
            return;
        } else if (MC.intQueueSize >= 2) {
            // the deque here will impact the input queue
            // owned by the MergerComponent
            queSortedLines1 = MC.dequeue();
            queSortedLines2 = MC.dequeue();

            // set the result queue equal to the sorted lines
            MergeSort.merge(queSortedLines1, queSortedLines2,  queResultQueue);

            // tell the MergerComponent that the process is done
            MC.mergeComplete(queResultQueue);
            queResultQueue = null;
        }
    }
}
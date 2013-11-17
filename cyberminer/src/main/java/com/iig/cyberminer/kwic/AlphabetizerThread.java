/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Thread to alphabetize lines.
 *  File              AlphabetizerThread.java
 *  Date:             10/07/2013
********************************************************************/
package com.iig.cyberminer.kwic;

public class AlphabetizerThread implements Runnable
{
    static int intThreadCount = 0;
    int intThreadID;
    private AlphabetizerComponent AC;
    private LinkedQueue queShiftedLines;

    // Create a AlphabetizerThread with the AlphabetizerComponent reference
    public AlphabetizerThread(AlphabetizerComponent ac)
    {
        intThreadID = ++intThreadCount;
        AC = ac;
    }

    // Interface - sort the lines from the shifter
    public void run()
    {
        // if the queue is empty then return
        if (AC.intQueueSize < 1) {
//System.out.println("Alpha Thread should not be here: " + AC.intQueueSize);
            return;
        }
        
        // the deque here will impact the input queue
        // owned by the AlphabetizerComponent
        queShiftedLines = AC.dequeue();

        // call mergeSort to alphabetize the shifted lines
        MergeSort.mergeSort(queShiftedLines);

/**************** begin debugging output *********************/
        System.out.println("Alpha thread: " + intThreadID);
/**************** end debugging output ***********************/

        // tell the AlphabetizerComponent that the process is done
        AC.processComplete(queShiftedLines);
    }
}
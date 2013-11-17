/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Object to handle alphabetizing the lines.
 *                    Code influenced by example 1 project from syllabus.
 *  File              AlphabetizerComponent.java
 *  Date:             9/23/2013
********************************************************************/
package com.iig.cyberminer.kwic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlphabetizerComponent
{
    // references to the merger component
    private MergerComponent mergerRef;

    // thread additions
    int intPoolSize;
    int intQueueSize;
    int intBusyThreads;
    final private LinkedQueue queProcessQueue;
    ExecutorService executor;

    // Create AlphabetizerComponent object with merger reference and pool size
    public AlphabetizerComponent(MergerComponent m, int poolSize)
    {
        mergerRef = m;
        intPoolSize = poolSize;
        intQueueSize = 0;
        intBusyThreads = 0;
        queProcessQueue = new LinkedQueue();
        executor = Executors.newFixedThreadPool(poolSize);
    }

    // Determine if a line is waiting for an available thread
    private void runThreads()
    {
        // while data is available and threads are available
        // instantiate threads to process data
        synchronized(this) {
            if (intQueueSize > 0 && intBusyThreads < intPoolSize) {
                Runnable alphaThread = new AlphabetizerThread(this);
                executor.execute(alphaThread);
                updateBusyThreads(1);
            }
        }
    }

    // Update the size of the processing queue
    synchronized void updateQueueSize(int i)
    {
        intQueueSize += i;
    }

    // Update the number of threads processing
    synchronized void updateBusyThreads(int i)
    {
        intBusyThreads += i;
    }

    // Shutdown the Executor service
    public void Shutdown()
    {
        executor.shutdown();
        while (!executor.isTerminated()) {}
    }

    // Put work in the queue to process
    public void processData(LinkedQueue lq)
    {
        // put work in the queue
        synchronized(queProcessQueue)
        {
            queProcessQueue.enqueue(lq);
        }

        updateQueueSize(1);

        // threads can now bring in data
        runThreads();
    }

    // wrapper for the process queue
    public LinkedQueue dequeue()
    {
        updateQueueSize(-1);

        synchronized(queProcessQueue)
        {
            return (LinkedQueue)queProcessQueue.dequeue();
        }
    }

    // display the output and notify the merger
    public void processComplete(LinkedQueue queTemp)
    {
        // send a list of lines to the merger
        mergerRef.processData(queTemp);
        updateBusyThreads(-1);

        // data is still waiting so bring it in
        if (intQueueSize > 0) {
            runThreads();
        }
    }
}
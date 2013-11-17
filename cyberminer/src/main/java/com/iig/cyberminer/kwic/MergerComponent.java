/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Object to handle the merging of the lines.
 *  File:             MergerComponent.java
 *  Date:             9/23/2013
********************************************************************/
package com.iig.cyberminer.kwic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MergerComponent
{
    private OutputComponent outputRef;

    // thread additions
    int intPoolSize;
    int intQueueSize;
    int intBusyThreads;
    final private LinkedQueue queProcessQueue;
    ExecutorService executor;

    // Create MergerComponent object with output reference
    public MergerComponent(OutputComponent o, int poolSize)
    {
        outputRef = o;
        intPoolSize = poolSize;
        intQueueSize = 0;
        intBusyThreads = 0;
        queProcessQueue = new LinkedQueue();
        executor = Executors.newFixedThreadPool(poolSize);
    }

    // Determine if lines are waiting for an available thread
    private void runThreads()
    {
        
        synchronized(this) {
            // if data is available and threads are available
            // then instantiate threads to process data
            if (intQueueSize > 0 && intBusyThreads < intPoolSize) {

                // merge two available input lines
                if (intQueueSize >= 2) {
                    MergerThread mergerThread = new MergerThread(this, outputRef);
                    executor.execute(mergerThread);
                } else
                
                // otherwise merge input lines with the Index queue
                // provided its not busy
                if (outputRef.isIndexLocked() == false) {
                    outputRef.lockIndex();

                    IndexMergeThread indexMergeThread =
                            new IndexMergeThread(this, outputRef);
                    executor.execute(indexMergeThread);
                }

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

    // place the merged lines into the process queue and run more threads
    public void mergeComplete(LinkedQueue queTemp)
    {
        // place the merged lines into the process queue
        queProcessQueue.enqueue(queTemp);
        updateQueueSize(1);
        updateBusyThreads(-1);

        // data is still waiting so bring it in
        if (intQueueSize > 0) {
            runThreads();
        }
    }

    // display the output and save the Indexed Queue
    public void processComplete(LinkedQueue queTemp)
    {
        // send the new Index to the output component
        outputRef.processData(queTemp);

        updateBusyThreads(-1);

        // unlock the Index
        outputRef.unlockIndex();

        // data is still waiting so bring it in
        if (intQueueSize > 0) {
            runThreads();
        }
    }
}
/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Object to handle shifting the words within a line.
 *  File              ShifterComponent.java
 *  Date:             9/23/2013
********************************************************************/
package com.iig.cyberminer.kwic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShifterComponent
{
    // references to the alphabetizer and output components
    private OutputComponent outputRef;

    // thread additions
    int intPoolSize;
    int intQueueSize;
    int intBusyThreads;
    final private LinkedQueue queProcessQueue;
    ExecutorService executor;

    // Create an ShifterComponent object with the Alpha and Output reference,
    // and the poolSize
    public ShifterComponent(OutputComponent o, int poolSize)
    {
        outputRef = o;
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
                Runnable shifterThread = new ShifterThread(this);
                executor.execute(shifterThread);
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
    public void processData(Line ln)
    {
        System.out.println( "Shifting " + ln );

        // put work in the queue
        synchronized(queProcessQueue)
        {
            queProcessQueue.enqueue(ln);            
        }

        updateQueueSize(1);
        
        // threads can now bring in data
        runThreads();
    }

    // wrapper for the process queue
    public Line dequeue()
    {
        updateQueueSize(-1);

        synchronized(queProcessQueue)
        {
            return (Line)queProcessQueue.dequeue();
        }
    }

    // display the output and notify the alphabetizer
    public void processComplete(LinkedQueue queTemp)
    {
        System.out.println( "Shifter thread has completed." );

        // output the shifted lines
        outputRef.saveData(queTemp);

        updateBusyThreads(-1);

        // data is still waiting so bring it in
        if (intQueueSize > 0) {
            runThreads();
        }
    }
}
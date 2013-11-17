package com.iig.cyberminer.kwic;

/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Object to control incoming lines
 *                      and process them to the shifter.
 *  File              InputComponent.java
 *  Date:             9/23/2013
********************************************************************/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InputComponent
{
    // references to the shifter and output components
    private ShifterComponent shifterRef;
    private OutputComponent outputRef;

    // thread additions
    int intPoolSize;
    int intQueueSize;
    int intBusyThreads;
    final private LinkedQueue queProcessQueue;
    ExecutorService executor;
    
    // Create an InputComponent object with the Shifter and Output reference,
    // and the poolSize
    public InputComponent(ShifterComponent s, OutputComponent o, int poolSize)
    {
        shifterRef = s;
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
                Runnable inputThread = new InputThread(this);
                executor.execute(inputThread);
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
    public void processData(URLtuple urlInput)
    {
        // put work in the queue
        synchronized(queProcessQueue)
        {
            queProcessQueue.enqueue(urlInput);
        }

        updateQueueSize(1);

        // threads can now bring in data
        runThreads();
    }

    // wrapper for the process queue
    public URLtuple dequeue()
    {
        updateQueueSize(-1);
        
        synchronized(queProcessQueue)
        {
            return (URLtuple)queProcessQueue.dequeue();
        }
    }

    // display the output and notify the shifter
    public void processComplete(LinkedQueue queTemp)
    {
        outputRef.printData(1, queTemp);

        // send a line to the shifter component
        shifterRef.processData((Line)queTemp.dequeue());
        shifterRef.processData((Line)queTemp.dequeue());

        updateBusyThreads(-1);

        // data is still waiting so bring it in
        if (intQueueSize > 0) {
            runThreads();
        }
    }
}
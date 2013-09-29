/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Object to output the lines.
 *  File:             OutputComponent.java
 *  Date:             9/23/2013
********************************************************************/

public class OutputComponent implements OutputInterface
{
    private Kwic            kwicRef;
    private LinkedQueue     queIndexedQueue;
    private boolean         blnLocked;

    // Create OutputComponent object with applet reference
    public OutputComponent(Kwic k)
    {
        kwicRef = k;
        blnLocked = false;
        queIndexedQueue = new LinkedQueue();
    }

    // Interface - stores the current Index
    public void setData(LinkedQueue q)
    {
        // save the alphabetized and merged lines
        queIndexedQueue = q;

        // print the Indexed Queue
        printIndex();
    }

    // Interface - returns the current Index
    public LinkedQueue getIndex()
    {
        return queIndexedQueue;
    }

    // Interface - lock the Index to prevent unsafe threading
    public synchronized void lockIndex()
    {
        blnLocked = true;
    }

    // Interface - unlock the Index to allow access to Index
    public synchronized void unlockIndex()
    {
        blnLocked = false;
    }

    // Interface - check if the Index is locked
    public synchronized boolean isIndexLocked()
    {
        return blnLocked;
    }

    // Interface - prints data from a passed in LinkedQueue
    public void printData(int intWindow, LinkedQueue lines)
    {
        Line    lneTemp;
        String  strLine = new String();

        // cycle through the passed lines and send to display
        for (int i=0; i<lines.getSize(); i++)
        {
            // reset the line
            strLine = "";
            strLine = (String)lines.dequeue().toString();
            lneTemp = new Line(strLine);
            lines.enqueue(lneTemp);
            kwicRef.display(intWindow, strLine);
        }

        //kwicRef.display(intWindow, "\n");
    }

    // Interface - prints the lines from the IndexedQueue
    public void printIndex()
    {
        this.printData(3, queIndexedQueue);
    }
}
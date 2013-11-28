package com.iig.cyberminer.kwic;

public class ShifterThread implements Runnable
{
    static int intThreadCount = 0;
    int intThreadID;
    private ShifterComponent SC;
    private LinkedQueue queShiftedLines;

    // Create a ShifterThread with the ShifterComponent reference
    public ShifterThread(ShifterComponent sc)
    {
        intThreadID = ++intThreadCount;
        SC = sc;
    }

    // Interface - shift the words from the input line
    public void run()
    {
        Line ln = null;

        // the deque here will impact the input queue
        // owned by the ShifterComponent
        try {
            if (SC.intQueueSize < 1)
                return;
            else
                ln = SC.dequeue();
        }
        catch (Exception e)
        {
            if (e.getMessage() != null)
                System.out.println("ShifterThread.run Exception: " +
                    e.getMessage());
            return;
        }

        String strURL = new String();
        String strLine = new String();
        String strTemp = new String();

        LinkedQueue queTemp = ln.getWordQueue();
        strURL = ln.getURL();
        strLine = ln.getLine();

        // set this line to be the original by setting the 3rd param to true
        Line lnNew = new Line(strURL, strLine, queTemp.toString(), true);
        int  intLines = queTemp.getSize();

        // queShiftedLines contains lines
        queShiftedLines = new LinkedQueue();

        // store the original line
        queShiftedLines.enqueue(lnNew);

        // cycle through the words and save new lines
        for (int i=0; i<intLines-1; i++) {
            strTemp=(String)queTemp.dequeue();
            queTemp.enqueue(strTemp);
            lnNew = new Line(strURL, strLine, queTemp.toString());

            // filter out noise words
            if (queTemp.getFront().toString().length() < 8) {
                if (!NoiseFilter.isNoiseWord(queTemp.getFront().toString())) {
                    queShiftedLines.enqueue(lnNew);
                }
            } else {
                queShiftedLines.enqueue(lnNew);
            }
        }

/**************** begin debugging output *********************/
//        System.out.println("Shifter thread: " + intThreadID);
/**************** end debugging output ***********************/

        // tell the ShifterComponent that the process is done
        SC.processComplete(queShiftedLines);
    }
}
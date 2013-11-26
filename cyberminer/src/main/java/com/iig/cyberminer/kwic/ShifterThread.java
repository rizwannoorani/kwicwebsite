/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Thread to shift lines.
 *  File              ShifterThread.java
 *  Date:             10/07/2013
********************************************************************/
package com.iig.cyberminer.kwic;

public class ShifterThread implements Runnable
{
    static int intThreadCount = 0;
    int intThreadID;
    private ShifterComponent SC;
    private LinkedQueue queShiftedLines;

    // build the ds for the noise words
    String[] arSingle = {"a","b","c","d","e","f","g","h","i",
                         "j","k","l","m","n","o","p","q","r",
                         "s","t","u","v","w","x","y","z","$",
                         "1","2","3","4","5","6","7","8","9","0","_"," "};
    String[] arDouble = {"an","as","at","be","by","do","he",
                         "if","in","is","it","me","my","of",
                         "on","or","to","up","we"};
    String[] arTriple = {"all","and","any","are","but","can",
                         "did","for","get","got","has","had",
                         "her","him","his","how","our","out",
                         "now","see","the","too","was","way","who","you"};
    String[] arQuad   = {"also","been","both","came","come",
                         "each","from","have","here","into",
                         "like","make","many","more","most",
                         "much","must","over","said","same",
                         "only","some","such","take","than",
                         "that","them","then","they","this",
                         "very","well","were","what","with","your"};
    String[] arFive   = {"after","about","being","could",
                         "might","never","other","since",
                         "still","their","there","these",
                         "those","under","where","which","while","would"};
    String[] arSix    = {"before","should"};
    String[] arSeven  = {"another","because","between","himself","through"};

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
                if (!isNoiseWord(queTemp.getFront().toString())) {
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

    // Returns true if the parameter is a noise word
    private boolean isNoiseWord(String strWord)
    {
        boolean blnNoiseWord = false;
        switch (strWord.length()) {
            case 1:
                for (int i=0; i<arSingle.length; i++)
                {
                    if (strWord.equals(arSingle[i]))
                    {
                        return true;
                    }
                }
            break;
            case 2:
                for (int i=0; i<arDouble.length; i++)
                {
                    if (strWord.equals(arDouble[i]))
                    {
                        return true;
                    }
                }
            break;
            case 3:
                for (int i=0; i<arTriple.length; i++)
                {
                    if (strWord.equals(arTriple[i]))
                    {
                        return true;
                    }
                }
            break;
            case 4:
                for (int i=0; i<arQuad.length; i++)
                {
                    if (strWord.equals(arQuad[i]))
                    {
                        return true;
                    }
                }
            break;
            case 5:
                for (int i=0; i<arFive.length; i++)
                {
                    if (strWord.equals(arFive[i]))
                    {
                        return true;
                    }
                }
            break;
            case 6:
                for (int i=0; i<arSix.length; i++)
                {
                    if (strWord.equals(arSix[i]))
                    {
                        return true;
                    }
                }
            break;
            case 7:
                for (int i=0; i<arSeven.length; i++)
                {
                    if (strWord.equals(arSeven[i]))
                    {
                        return true;
                    }
                }
            break;
        }

        return blnNoiseWord;
    }
}
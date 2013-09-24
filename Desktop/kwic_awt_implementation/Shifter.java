/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Object to shift the words within a line.
 *  File              Shifter.java
 *  Date:             9/23/2013
********************************************************************/

import java.io.*;

public class Shifter extends Thread implements Serializable
{
    private LinkedWords     queWords;
    private Kwic            kwicRef;
    private LinkedWords[]   arShiftedLines;

    // Create Shifter object from the passed line
    public Shifter(Kwic k, String n, LinkedWords q)
    {
        super(n);
        kwicRef = k;
        queWords = q;
        start();
    }
    
    @Override
    public void run()
    {
        String strTemp = new String();
        
        LinkedWords queNewLine = copyLine(queWords);

        arShiftedLines = new LinkedWords[queWords.getSize()];

        arShiftedLines[0] = queNewLine;

        for (int i=0; i<queNewLine.getSize()-1; i++) {
            queNewLine = copyLine(arShiftedLines[i]);
            strTemp=(String)queNewLine.dequeue();
            queNewLine.enqueue(strTemp);
            arShiftedLines[i+1] = queNewLine;
        }

        displayShift();

        // create a Alphabetizer object to peform the line sorting
        instantiateAlphabetizer();
    }

    private void instantiateAlphabetizer()
    {
        // place the array of shifted lines into a queue
        LinkedWords shiftedLines = new LinkedWords();
        String strLine = new String();
        String strTemp = new String();

        for (int i=0; i<arShiftedLines.length; i++)
        {
            // reset the line
            strLine = "";
            
            for (int j=0; j<arShiftedLines[i].getSize(); j++)
            {
                strTemp = (String)arShiftedLines[i].dequeue();
                strLine+=strTemp;
                strLine+=" ";
                arShiftedLines[i].enqueue(strTemp);
            }

            shiftedLines.enqueue(strLine);
        }
        
        Alphabetizer sort = new Alphabetizer(kwicRef, "thread 1b", shiftedLines);
        kwicRef.displayMerge(this.getName() + " : I called the alphabetizer");
    }

    private void displayShift()
    {
        String strLine = new String();
        String strTemp = new String();

        for (int i=0; i<arShiftedLines.length; i++)
        {
            // reset the line
            strLine = "";
            
            for (int j=0; j<arShiftedLines[i].getSize(); j++)
            {
                strTemp = (String)arShiftedLines[i].dequeue();
                strLine+=strTemp;
                strLine+=" ";
                arShiftedLines[i].enqueue(strTemp);
            }

            kwicRef.displayShift(this.getName() + " : " + strLine);
        }
    }

    private LinkedWords copyLine(LinkedWords lw)
    {
        LinkedWords queNewLine = new LinkedWords();
        String strTemp = new String();

        for (int i=0; i<lw.getSize(); i++)
        {
            strTemp = (String)lw.dequeue();
            lw.enqueue(strTemp);
            queNewLine.enqueue(strTemp);
        }

        return queNewLine;
    }
}

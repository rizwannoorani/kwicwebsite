/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Object to shift the words within a line.
 *  File              ShifterComponent.java
 *  Date:             9/23/2013
********************************************************************/

public class ShifterComponent implements ShifterInterface
{
    private AlphabetizerComponent alphaRef;
    private OutputComponent outputRef;
    private LinkedQueue     queShiftedLines;

    // Create Shifter object with alpha reference
    public ShifterComponent(AlphabetizerComponent a, OutputComponent o)
    {
        alphaRef = a;
        outputRef = o;
    }

    // Interface - accepts a line and creates shifte lines
    public void setData(Line ln)
    {
        String strTemp = new String();
        LinkedQueue queTemp = ln.getWordQueue();
        Line lnNew = new Line(queTemp.toString());

        // queShiftedLines contains lines
        queShiftedLines = new LinkedQueue();

        // store the original line
        queShiftedLines.enqueue(lnNew);

        // cycle through the words and save new lines
        for (int i=0; i<queTemp.getSize()-1; i++) {
            strTemp=(String)queTemp.dequeue();
            queTemp.enqueue(strTemp);
            lnNew = new Line(queTemp.toString());
            queShiftedLines.enqueue(lnNew);
        }

        outputRef.printData(2, queShiftedLines);

        alphaRef.setData(queShiftedLines);
    }
}
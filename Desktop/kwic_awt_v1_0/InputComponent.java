/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Object to control the accepting 
 *                      and storing of lines.
 *  File              InputComponent.java
 *  Date:             9/23/2013
********************************************************************/

public class InputComponent implements InputInterface
{
    private Line lneInput;
    private ShifterComponent shifterRef;
    private OutputComponent outputRef;
    
    // Create an InputComponent object with the Shifter and Output reference
    public InputComponent(ShifterComponent s, OutputComponent o)
    {
        shifterRef = s;
        outputRef = o;
    }

    // Interface - set the line for the Input
    public void setData(String s)
    {
        lneInput = new Line(s);

        LinkedQueue queTemp = new LinkedQueue();
        queTemp.enqueue(lneInput);

        outputRef.printData(1, queTemp);

        shifterRef.setData(lneInput);
    }

    // return the original line
    @Override
    public String toString()
    {
        return lneInput.toString();
    }
}
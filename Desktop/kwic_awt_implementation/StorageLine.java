/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Data object to store a line. Code influenced by
 *                    example 1 project from syllabus.
 *  File              StorageLine.java
 *  Date:             9/23/2013
********************************************************************/

import java.io.*;
import java.util.*;

public class StorageLine extends Thread implements Serializable
{
    private String      strLine;
    private Line        lneStorage;
    private Kwic        kwicRef;

    // Create Storage Line object from the passed string
    public StorageLine(Kwic k, String n, String s)
    {
        super(n);
        strLine = s;
        kwicRef = k;
        start();
    }

    //seperate the line into words and place them into a queue
    @Override
    public void run()
    {
        lneStorage = new Line(strLine);

        // send original line back to applet
        displayOriginal();
        
        // create a shifter object to peform the line shifting
        instantiateShifter();
    }

    private void instantiateShifter()
    {
        Shifter shift = new Shifter(kwicRef, "thread 1a", lneStorage.getWordQueue());
    }

    // send original line back to applet
    private void displayOriginal()
    {
        kwicRef.displayOriginal(this.getName() + " : " + strLine);
    }

    // return the original line
    @Override
    public String toString()
    {
        return strLine;
    }
}
/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Data object to store a line.  Influenced by
 *                    example 1 project from syllabus.
 *  File              Line.java
 *  Date:             9/24/2013
********************************************************************/

import java.util.*;

public class Line
{
    private String      strLine;
    private LinkedQueue queWords;
    private int         intNumWords;

    // Create Storage Line object from the passed string
    public Line(String s)
    {
        strLine=s;
        StringTokenizer stk = new StringTokenizer (strLine," ");
        intNumWords=stk.countTokens();
        queWords = new LinkedQueue();

        for(int i=0;i<intNumWords;i++)
        {
            queWords.enqueue(stk.nextToken());
        }
    }

    // return the words from the line
    public LinkedQueue getWordQueue()
    {
        return queWords;
    }

    // return the number of words in a line
    public int getWordNum()
    {
        return intNumWords;
    }

    // return the original line
    @Override
    public String toString()
    {
        return strLine;
    }
}
/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Data object to store a line.  Influenced by
 *                    example 1 project from syllabus.
 *  File              Line.java
 *  Date:             9/24/2013
********************************************************************/
package com.iig.cyberminer.kwic;

import java.util.*;

public class Line
{
    private String      strURL;
    private String      strLine;
    private LinkedQueue queWords;
    private int         intNumWords;
    private boolean     blnOriginal = false;

    // Create Storage Line object from the passed string
    public Line(String u, String s)
    {
        strURL=u;
        strLine=s;
        StringTokenizer stk = new StringTokenizer (strLine," ");
        intNumWords=stk.countTokens();
        queWords = new LinkedQueue();

        for(int i=0;i<intNumWords;i++)
        {
            queWords.enqueue(stk.nextToken());
        }
    }

    // Create Storage Line object from the passed string
    // and a boolean to determine if it is an original line or not
    public Line(String u, String s, boolean blnOrig)
    {
        blnOriginal = blnOrig;
        strURL=u;
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

    // return true if this is an original line
    public boolean isOriginal()
    {
        return blnOriginal;
    }

    // return the url
    public String getURL()
    {
        return strURL;
    }

    // return the original line
    @Override
    public String toString()
    {
        return strLine;
    }
}
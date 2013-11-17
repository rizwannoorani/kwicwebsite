/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Data object to store a the original url
 *                      and description.
 *  File              URLtuple.java
 *  Date:             11/2/2013
********************************************************************/
package com.iig.cyberminer.kwic;

public class URLtuple
{
    private String      strURL;
    private String      strDesc;

    // Create Data Structure to store the url and description
    public URLtuple(String u, String s)
    {
        strURL=u;
        strDesc=s;
    }

    // return the url
    public String getURL()
    {
        return strURL;
    }

    // return the description
    public String getDescription()
    {
        return strDesc;
    }

    // return the url and description
    @Override
    public String toString()
    {
        return strURL + " " + strDesc;
    }
}
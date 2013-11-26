/********************************************************************
 *  Program:          Cyberminer Search Engine
 *  Programmer:       Dan Modesto
 *  Purpose:          Data object to store a the query results.
 *  File              QueryResult.java
 *  Date:             11/16/2013
********************************************************************/
package com.iig.cyberminer.kwic;

public class QueryResult
{
    private String      strKeyWord;
    private String      strURL;
    private String      strDesc;

    // Create Data Structure to store the keyword, url, and description
    public QueryResult(String k, String u, String s)
    {
        strKeyWord=k;
        strURL=u;
        strDesc=s;
    }

    // return the keyword
    public String getKeyWord()
    {
        return strKeyWord;
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
        return strKeyWord + " " + strURL + " " + strDesc;
    }
}
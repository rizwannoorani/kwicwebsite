/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Thread to accept lines.
 *  File              InputThread.java
 *  Date:             10/07/2013
********************************************************************/
package com.iig.cyberminer.kwic;

import org.apache.commons.validator.routines.UrlValidator;

public class InputThread implements Runnable
{
    static int intThreadCount = 0;
    int intThreadID;
    private InputComponent IC;
    private String[] strSchemes = {"http","https"};
    private UrlValidator urlValidator;

    // Create an InputThread with the InputComponent reference
    public InputThread(InputComponent ic)
    {
        intThreadID = ++intThreadCount;
        IC = ic;
        urlValidator = new UrlValidator(strSchemes);
    }

    // Interface - set the line for the Input
    public void run()
    {
        System.out.println( " IM A THREAD! " );

        URLtuple urlData = null;
        
        // the deque here will impact the input queue
        // owned by the InputComponent
        try {
            if (IC.intQueueSize < 1)
                return;
            else
                urlData = (URLtuple)IC.dequeue();
        }
        catch (Exception e)
        {
            if (e.getMessage() != null)
                System.out.println("InputThread.run Exception: " +
                    e.getMessage());
            return;
        }

        // if the url is invalid then simply remove it from the queue
        if (!urlValidator.isValid(urlData.getURL())) {
            IC.updateBusyThreads(-1);
            return;
        }

        // create the DS to store the URL and Description
        Line lneDesc = new Line(urlData.getURL(),
                                 urlData.getDescription(),
                                 urlData.getDescription(), true);

        // parse the url text to be shifted and indexed
        String strURL = buildURLString(urlData.getURL());

        // create the DS to store the URL and the URL parameters
        Line lneURL = new Line(urlData.getURL(),
                               urlData.getDescription(), strURL);

        // place the url and the description into the queue
        LinkedQueue queTemp = new LinkedQueue();
        queTemp.enqueue(lneDesc);
        queTemp.enqueue(lneURL);

        // tell the InputComponent that the process is done
        IC.processComplete(queTemp);
    }

    // parse the url text to components to be shifted and indexed
    private String buildURLString(String s)
    {
        // ex. http://video.google.co.uk:80/videoplay?docid=-7246927612831078230
        //            &hl=en#00h02m30s

        String strIndexURL = new String();
        String strURL = new String();

        strURL=s;
        strIndexURL = strURL;

        // remove the protocol
        if (strURL.substring(0, 7).toLowerCase().equals("http://")) {
            strURL = strURL.substring(7, strURL.length());
        } 
        else 
        if (strURL.substring(0, 8).toLowerCase().equals("https://")) {
            strURL = strURL.substring(8, strURL.length());
        }
        strIndexURL += " " + strURL;

        // remove the url parameters
        if (strURL.indexOf("?")>0)
        {
            strURL = strURL.substring(0,strURL.indexOf("?"));
            strIndexURL += " " + strURL;
        }

        // remove directories
        while(strURL.indexOf("/")>0)
        {
            strURL = strURL.substring(0,strURL.lastIndexOf("/"));
            strIndexURL += " " + strURL;
        }

        //ex. strHostname = video.google.co.uk:80
        String strHostname = new String();
        String strTemp = strURL;        

        // remove the subdomain, usually www
        // ex. video.google.co.uk:80 google.co.uk:80
        strTemp = strTemp.substring(strTemp.indexOf(".")+1);
        strHostname += " " + strTemp;

        // remove the port if it exists
        // ex. video.google.co.uk:80 google.co.uk:80 google.co.uk
        if (strTemp.indexOf(":")>0)
        {
            strTemp = strTemp.substring(0,strTemp.lastIndexOf(":"));
            strHostname += " " + strTemp;
        }

        // remove the top level domain if it exists
        // ex. video.google.co.uk:80 google.co.uk:80 google.co.uk google.co
        if (strTemp.indexOf(".")>0)
        {
            strTemp = strTemp.substring(0,strTemp.lastIndexOf("."));
            strHostname += " " + strTemp;
        }        
        
        // remove all other domain levels
        // video.google.co.uk:80 google.co.uk:80 google.co.uk google.co google
        while(strTemp.indexOf(".")>0)
        {
            strTemp = strTemp.substring(0,strTemp.lastIndexOf("."));
            strHostname += " " + strTemp;
        }

        strIndexURL += " " + strHostname;

        return strIndexURL;
    }
}
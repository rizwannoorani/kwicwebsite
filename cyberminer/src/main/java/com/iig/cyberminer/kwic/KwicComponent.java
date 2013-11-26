/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Creates an object to provide to
 *                      perform and save the indexing.
 *  File              KwicComponent.java
 *  Date:             11/10/2013
********************************************************************/
package com.iig.cyberminer.kwic;

public class KwicComponent implements KwicInterface {

    // create the kwic index components
    OutputComponent output;
    ShifterComponent shifter;
    InputComponent input;

    int intPoolSize;

    public KwicComponent(int intPS)
    {
        // how many threads will be in the pool
        if (intPS > 1)
        {
            intPoolSize = intPS;
        }
        else
        {
            intPoolSize = 1;
        }

        // instantiate the Kwic components
        output = new OutputComponent(this);
        shifter = new ShifterComponent(output, intPoolSize);
        input = new InputComponent(shifter, output, intPoolSize);
    }

    // Accept a url and descrtiption and then index
    public void processData(String strUrl, String strDesc) {
        System.out.println( "Processing data where url =" + strUrl + " and desc=" + strDesc );
        URLtuple urlInput = new URLtuple(strUrl, strDesc);
        input.processData(urlInput);
    }
}
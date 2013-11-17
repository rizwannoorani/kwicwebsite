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
    MergerComponent merger;
    AlphabetizerComponent alpha;
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
        merger = new MergerComponent(output, intPoolSize);
        alpha = new AlphabetizerComponent(merger, intPoolSize);
        shifter = new ShifterComponent(alpha, output, intPoolSize);
        input = new InputComponent(shifter, output, intPoolSize);
    }

    // Accept a url and descrtiption and then index
    public void processData(String strUrl, String strDesc) {
        URLtuple urlInput = new URLtuple(strUrl, strDesc);
        input.processData(urlInput);
    }

    // print the index
    public void print()
    {
        output.printIndex();
    }

    // display the lines within the appropriate output window
    public void display(int i, String s)
    {
        switch(i) {
            case 1:
                System.out.println("Window 1: " + s);
            break;
            case 2:
                System.out.println("Window 2: " + s);
            break;
            case 3:
                System.out.println("Window 3: " + s);
            break;
        }
    }

    // add a new line to the requested output window
    public void addNewLine(int i)
    {
        switch(i) {
            case 1:
                System.out.println("Window 1: newline");
            break;
            case 2:
                System.out.println("Window 2: newline");
            break;
            case 3:
                System.out.println("Window 3: newline");
            break;
        }
    }
}
/********************************************************************
 *  Program:          KWICIndexer
 *  Programmer:       Dan Modesto
 *  Purpose:          Application to test the KwicIndexer.
 *  File              Main.java
 *  Date:             11/10/2013
********************************************************************/
package com.iig.cyberminer.kwic;

/**
 *
 * @author Dan
 */
public class KwicService {

    public KwicService() {

    }

    public String processData( String url, String contents ) {
        return "Indexing goes here. ( " + url + ", " + contents + " )"; 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        KwicComponent kwicComponent = new KwicComponent(5);

        kwicComponent.processData("http://www.a.com", "a b c");
        kwicComponent.processData("http://www.b.com", "ab cd ef");
        kwicComponent.processData("http://www.c.com", "abc def");
        kwicComponent.processData("http://www.d.com", "abc def ghi");
        kwicComponent.processData("http://www.e.com", "mn pq rs");
        kwicComponent.processData("http://www.f.com", "pqr stu");
        kwicComponent.processData("http://www.g.com", "s t u");
        kwicComponent.processData("http://www.h.com", "v");
        kwicComponent.processData("http://www.i.com", "w x");
        kwicComponent.processData("http://www.j.com", "y z");
    }
}
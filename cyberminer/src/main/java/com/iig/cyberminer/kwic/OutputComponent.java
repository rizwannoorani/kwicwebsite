/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Object to output the lines.
 *  File:             OutputComponent.java
 *  Date:             9/23/2013
********************************************************************/
package com.iig.cyberminer.kwic;

import com.iig.cyberminer.db.DatabaseComponent;

import java.io.*;
import java.sql.*;

public class OutputComponent
{
    private DatabaseComponent dbConn;

    // Create OutputComponent object with applet reference
    public OutputComponent(KwicComponent k)
    {
        // create teh database component
        try {
            dbConn = new DatabaseComponent();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    // Save the keywords, urls, and thier descriptions to the database
    public void saveData(LinkedQueue lines)
    {
        int     intLines = lines.getSize();
        Line    lneTemp;

        String strInsertQuery = "INSERT INTO `url_index` VALUES ";

        // cycle through the passed lines and build the INSERT statement
        for (int i=0; i<intLines; i++)
        {
            lneTemp = (Line)lines.dequeue();

            strInsertQuery += "('" +
                lneTemp.getKeyWord() + "','" +
                lneTemp.getURL() + "','" +
                lneTemp.getLine() + "'," +
                "CURRENT_TIMESTAMP),";
        }

        // remove the last comma
        strInsertQuery = strInsertQuery.substring(0,strInsertQuery.length()-1);

        // when duplicate keys occur update the timestamp to note the recent URL
        strInsertQuery += " ON DUPLICATE KEY " +
                "UPDATE Updated = CURRENT_TIMESTAMP;";

        System.out.println(strInsertQuery);

        // Execute the insert statement
        try {
            dbConn.executeUpdate(strInsertQuery);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println( "You really made it!" );
    }
}
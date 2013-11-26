/********************************************************************
 *  Program:          Cyberminer Search Engine
 *  Programmer:       Dan Modesto
 *  Purpose:          Object to handle the database connection.
 *  File              DatabaseComponent.java
 *  Date:             11/16/2013
********************************************************************/
package com.iig.cyberminer.db;

import com.iig.cyberminer.kwic.LinkedQueue;
import com.iig.cyberminer.kwic.QueryResult;

import java.io.*;
import java.sql.*;

public class DatabaseComponent
{
    private String strDB_User;
    private String strDB_Pass;
    private String strDatabaseURL;
    private String strDatabaseName;

    // Create DatabaseComponent object to handle database connections and ops
    public DatabaseComponent() throws SQLException, IOException
    {
        strDB_User      = "UserCyberminer";
        strDB_Pass      = "renimrebycFall13";
        strDatabaseURL  = "localhost";
        strDatabaseName = "cyberminer";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException x) {
            System.out.println("Driver could not be loaded.");
        }
    }

    // Create a connection with the database
    private Connection GetConnection() throws SQLException, IOException
    {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection
                ("jdbc:mysql://" + strDatabaseURL +
                 "/" + strDatabaseName,strDB_User,strDB_Pass);
        }
        catch(SQLException e)
        {   
            System.out.println("GetConnection SQLException: " + e.getMessage());
        }

        return conn;
    }

    // Execute the passed in SELECT query
    public LinkedQueue executeSelect(String q) throws SQLException, IOException
    {
        String  strKeyWord;
        String  strURL;
        String  strDesc;
        LinkedQueue queResultSet = new LinkedQueue();

        Connection conn = null;
        Statement conStatement = null;

        try {
            conn = GetConnection();

            conStatement = conn.createStatement();
            ResultSet result = conStatement.executeQuery(q);

            while (result.next())
            {
                strKeyWord = result.getString(1);
                strURL = result.getString(2);
                strDesc = result.getString(3);

                QueryResult qryResult =
                    new QueryResult(strKeyWord, strURL, strDesc);

                queResultSet.enqueue(qryResult);
            }
        }
        catch(SQLException e)
        {
            System.out.println("executeSelect SQLException: " + e.getMessage());
        }
        finally
        {
            if(conStatement != null)
            {
                conStatement.close();
            }

            if(conn != null)
            {
                conn.close();
            }
        }

        return queResultSet;
    }

    // Execute passed in INSERT/UPDATE/DELETE query
    public boolean executeUpdate(String q) throws SQLException, IOException
    {
        Connection conn = null;
        Statement conStatement = null;
        boolean blnSuccess = true;

        try {
            conn = GetConnection();

            conStatement = conn.createStatement();
            conStatement.executeUpdate(q);
        }
        catch(SQLException e)
        {
            blnSuccess = false;
            System.out.println("executeUpdate SQLException: " + e.getMessage());
        }
        finally
        {
            if(conStatement != null)
            {
                conStatement.close();
            }

            if(conn != null)
            {
                conn.close();
            }
        }

        return blnSuccess;
    }
}
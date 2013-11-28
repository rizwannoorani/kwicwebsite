package com.iig.cyberminer.search;

import com.iig.cyberminer.db.DatabaseComponent;
import com.iig.cyberminer.kwic.LinkedQueue;
import com.iig.cyberminer.kwic.QueryResult;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class QueryComponent {
	private DatabaseComponent dbComp; 
	
	//Constructor
	QueryComponent() {
		try {
			this.dbComp = new DatabaseComponent();
		} catch (Exception e) {
			System.out.println( "Error creating DB component." );
			e.printStackTrace();
			this.dbComp = null;
		}
	}

	//This function produces Map of URL and description for the calling class
	public Map<String,String> processData( ArrayList<String> inputValues, String queryType ){
		if( this.dbComp == null ) {
			System.out.println( "Database is not configured. Skipping query for: " + inputValues );
			return null;
		}

		Map<String,String> queryResult = new ConcurrentHashMap<String,String>();

		String queryString = null; 
		if (queryType.equalsIgnoreCase("and") || queryType.equalsIgnoreCase("or")) {
			queryString = getAndOrQueryString( inputValues );
		} else if( queryType.equalsIgnoreCase("not") ) { 
			queryString = getNotQueryString( inputValues );
		} else {
			System.out.println( "Unrecognized query type. Exiting query." );
			return null;
		}

		try{
			if (queryString != null) {
				LinkedQueue result = dbComp.executeSelect(queryString);
				for (int i = 0 ; i < result.size() ; ++i) {
					QueryResult rslt = (QueryResult) result.get(i);
					if (!queryResult.containsKey(rslt.getURL()))
						queryResult.put(rslt.getURL(), rslt.getDescription());
				}
			}	
		} catch(Exception e) {
			System.out.println( "Error running query." );
			e.printStackTrace();
			return null;
		}
	 
		return queryResult;	
   }
	
	//This function process the input keyword array into a executable query string for and/or logic and return it to processData()
	private String getAndOrQueryString( ArrayList<String> queryValues ){
		String queryString = null;
		if (queryValues != null){
			queryString = queryValues.toString().replaceAll(", ","','");
			queryString = queryString.replaceAll("\\[(.*?)\\]", "$1");
			queryString = "SELECT * FROM URL_INDEX WHERE WORD IN ('"+queryString+"')";
		}
		
		System.out.println( "AND/OR query string: " + queryString );
		
		return queryString;
	}
	
	//This function process the input keyword array into a executable query string for not logic and return it to processData()
	private String getNotQueryString( ArrayList<String> queryValues ){
		String queryString = null ;
		if (queryValues.size() > 0){
			queryString = "SELECT * FROM URL_INDEX "
					+ "	WHERE WORD NOT LIKE '%"+queryValues.get(0)+"%'";
			for (int i = 1 ; i < queryValues.size() ; ++ i){
				queryString += " And Word NOT LIKE "+"'%"+queryValues.get(i)+"%'";
			}
		}	
		
		System.out.println( "NOT query string: " + queryString );
		
		return queryString;
	}
	
	
}
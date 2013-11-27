package com.iig.cyberminer.search;

import com.iig.cyberminer.db.DatabaseComponent;
import com.iig.cyberminer.kwic.LinkedQueue;
import com.iig.cyberminer.kwic.QueryResult;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class QueryComponent {
	
	private Map<String,String> queryResult; 
	private ArrayList<String> queryValues;
	private String searchType ;
	private DatabaseComponent dbComp; 
	
	//Constructor
	QueryComponent(ArrayList<String> inputValues, String queryType){
		this.queryValues = inputValues;
		this.searchType = queryType;
		this.queryResult = new HashMap<String,String>();
		try {
			this.dbComp = new DatabaseComponent();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	//This function produces Map of URL and description for the calling class
	public Map<String,String> processData(){
		
		String queryString = null; 
		if (this.searchType.equalsIgnoreCase("and") || this.searchType.equalsIgnoreCase("or"))
			queryString = this.getAndOrQueryString();
		else 
			queryString = this.getNotQueryString();
			try{
				if (queryString != null){
				LinkedQueue result = dbComp.executeSelect(queryString);
				for (int i = 0 ; i < result.size() ; ++i){
					QueryResult rslt = (QueryResult) result.get(i);
					if (!this.queryResult.containsKey(rslt.getURL()))
						this.queryResult.put(rslt.getURL(), rslt.getDescription());
				}
			 }	
			}catch(Exception e){
				e.printStackTrace();
			}
		
	 
		return this.queryResult;
	
   }
	
	//This function process the input keyword array into a executable query string for and/or logic and return it to processData()
	private String getAndOrQueryString(){
		String queryString = null;
		if (this.queryValues != null){
			queryString = this.queryValues.toString().replaceAll(", ","','");
			queryString = queryString.replaceAll("\\[(.*?)\\]", "$1");
			queryString = "SELECT * FROM URL_INDEX WHERE WORD IN ('"+queryString+"')";
		}
		
		return queryString;
	}
	
	//This function process the input keyword array into a executable query string for not logic and return it to processData()
	private String getNotQueryString(){
		String queryString = null ;
		if (this.queryValues.size() > 0){
			queryString = "SELECT * FROM URL_INDEX "
					+ "	WHERE WORD NOT LIKE '%"+this.queryValues.get(0)+"%'";
			for (int i = 1 ; i < this.queryValues.size() ; ++ i){
				queryString += " And Word NOT LIKE "+"'%"+this.queryValues.get(i)+"%'";
			}
		}	
		
		
		return queryString;
	}
	
	
}
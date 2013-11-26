package com.iig.cyberminer.search;

import java.util.ArrayList;

public class CyberminerService {

	private ParserComponent parseComp;
	private String queryString;
	private String queryType;
	
	public CyberminerService()
	{
		parseComp = new ParserComponent();
		//QueryComonent queryComp = new QueryComponent();
		//MergerComponent
	}
	
	public String getSearchResults(String type, String query) {
		queryType = type;
		queryString = query;
		ArrayList<String> parsedArray = parseComp.processData(queryString);
		//Map<String, String> queryResult = queryComp.processData(parsedArray);
		//mergerComponent process data
		return "Search results go here.";
	}
}
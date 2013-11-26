package com.iig.cyberminer.search;

public class CyberminerService {

	String queryString;
	String queryType;
	
	public CyberminerService()
	{
		ParseComponent parseComp = new ParseComponent();
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
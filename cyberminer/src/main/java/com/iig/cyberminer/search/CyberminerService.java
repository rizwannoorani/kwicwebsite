package com.iig.cyberminer.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CyberminerService {

	private ParserComponent parseComp;

	public CyberminerService() {
		parseComp = new ParserComponent();
		//QueryComonent queryComp = new QueryComponent();
		//MergerComponent
	}
	
	public Map<String, String> getSearchResults(String type, String query) {
		ArrayList<String> parsedArray = parseComp.processData( query );

		//Map<String, String> queryResult = queryComp.processData(parsedArray);

		//mergerComponent process data

		//Returning test data to get back out to UI
		Map<String, String> resultsMap = new HashMap<String, String>();
		resultsMap.put( "www.a.com", "For all of your finest A needs." );
		resultsMap.put( "www.b.net", "The black hole of the internet." );

		return resultsMap;
	}
}
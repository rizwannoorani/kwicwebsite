package com.iig.cyberminer.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CyberminerService {

	private ParserComponent parseComp;
	private QueryComponent queryComp;
	private MergeComponent mergeComp;

	public CyberminerService() {
		parseComp = new ParserComponent();
		queryComp = new QueryComponent();
		mergeComp = new MergeComponent();
	}
	
	public Map<String, String> getSearchResults(String type, String query) {
        if( query.equals( "" ) ) {
            return new HashMap<String, String>();
        }

		ArrayList<String> parsedArray = parseComp.processData( query );

		Map<String, String> queryResults = queryComp.processData( parsedArray, type );
		if( queryResults == null ) {
			throw new RuntimeException( "Error encountered retrieving query results." );
		} else {
			System.out.println( "Results retrieved: " + queryResults.toString() );
		}

		return mergeComp.processData( queryResults, type, parsedArray );
	}
}
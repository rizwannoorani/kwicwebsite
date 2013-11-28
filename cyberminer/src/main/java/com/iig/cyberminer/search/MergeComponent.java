package com.iig.cyberminer.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;



public class MergeComponent {

	//Constructor
	MergeComponent() { }

	public Map<String, String> processData( Map<String, String> queryResults, String queryType, ArrayList<String> queryWords ) {

        if( queryType.equalsIgnoreCase( "OR" ) ) {
            System.out.println( "Skipping OR filter. (Does nothing)" );
            return queryResults;
        } else if( queryType.equalsIgnoreCase( "AND" ) ) {
            return andFilterResults( queryResults, queryWords );
        } else if( queryType.equalsIgnoreCase( "NOT" ) ) {
            return notFilterResults( queryResults, queryWords );
        } else {
			throw new RuntimeException( "Invalid queryType received." );
        }

	}

	public Map<String, String> andFilterResults( Map<String, String> queryResults, ArrayList<String> queryWords ) {
		System.out.println( "Running AND filter." );

        String currentUrl;
        String currentDesc;
        Iterator iter = queryResults.keySet().iterator();
		while( iter.hasNext() ) {
            currentUrl = iter.next().toString();
            currentDesc = queryResults.get( currentUrl );
            System.out.println( "Examining pair: " + currentUrl + "," + currentDesc );
            for( String queryKey: queryWords ) {
                System.out.println( "Checking for " + queryKey + " in " + currentDesc );
                if( !currentDesc.toLowerCase().contains( queryKey.toLowerCase() ) ) {
                    System.out.println( queryKey + " not found in description. Throwing away." );
                    queryResults.remove( currentUrl );
                    break;
                }
            }
		}

		//get rid of this
		return queryResults;
	}

	public Map<String, String> notFilterResults( Map<String, String> queryResults, ArrayList<String> queryWords ) {
		System.out.println( "Running NOT filter." );

        String currentUrl;
        String currentDesc;
        Iterator iter = queryResults.keySet().iterator();
        while( iter.hasNext() ) {
            currentUrl = iter.next().toString();
            currentDesc = queryResults.get( currentUrl );
            System.out.println( "Examining pair: " + currentUrl + "," + currentDesc );
            for( String queryKey: queryWords ) {
                System.out.println( "Checking for " + queryKey + " in " + currentDesc );
                if( currentDesc.toLowerCase().contains( queryKey.toLowerCase() ) ) {
                    System.out.println( queryKey + " found in description. Throwing away." );
                    queryResults.remove( currentUrl );
                    break;
                }
            }
        }

		//get rid of this
		return queryResults;
	}
	
}
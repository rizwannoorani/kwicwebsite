package com.iig.cyberminer.search;

import com.iig.cyberminer.kwic.NoiseFilter;

import java.util.ArrayList;
import java.util.Collections;

//NoiseFilter, isNoiseWord return bool
public class ParserComponent {

	public ArrayList<String> processData(String search)
	{
		ArrayList<String> result = new ArrayList<String>();
		
		String[] split = search.split(" ");
		
		Collections.addAll(result, split);
		
		for(int i = 0; i < result.size(); i++)
		{
			if(NoiseFilter.isNoiseWord(result.get(i)))
			{
				result.remove(i);
			}
		}
		
		return result;
	}
}

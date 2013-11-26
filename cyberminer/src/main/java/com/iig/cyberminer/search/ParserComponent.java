import java.util.ArrayList;
import java.util.Collections;


public class ParserComponent {

	public ArrayList<String> processData(String search)
	{
		ArrayList<String> result = new ArrayList<String>();
		
		String[] split = search.split(" ");
		
		Collections.addAll(result, split);
		
//		for(int i = 0; i < split.length; i++)
//		{
//			result.add(split[i]);
//		}
		
		return result;
	}
}

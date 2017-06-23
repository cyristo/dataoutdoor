package dataoutdoor.contract;

import java.util.LinkedHashMap;

public interface DataTransformerEngine {

	public LinkedHashMap<String, Object> transform(LinkedHashMap<String, Object> dataset);
	
}
